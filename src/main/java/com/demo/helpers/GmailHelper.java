package com.demo.helpers;

import com.demo.utils.Constants;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;
import lombok.SneakyThrows;
import org.apache.commons.lang3.time.StopWatch;
import org.testng.Assert;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

public class GmailHelper {
    private static final String APPLICATION_NAME = "application name";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "src/test/resources/tokens";

    /**
     * Global instance of the scopes.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(GmailScopes.MAIL_GOOGLE_COM);
    private static final String CREDENTIALS_FILE_PATH = "src/test/resources/tokens/client_secret.json";

    private final Gmail gmailService;
    private static final long MESSAGE_LIST_MAX_RESULTS = 5L;
    private String query;
    private List<Message> messages;

    @SneakyThrows
    public GmailHelper(String query) {
        this();
        this.query = query;
    }

    @SneakyThrows
    public GmailHelper() {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        this.gmailService = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
            .setApplicationName(APPLICATION_NAME)
            .build();
    }

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the StoredCredential file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = new FileInputStream(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
            HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
            .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
            .setAccessType("offline")
            .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8889).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    @SneakyThrows
    public GmailHelper executeQueryWithWaitingTime() {
        StopWatch sw = new StopWatch();
        sw.start();
        int timeInMilliseconds = Constants.EMAIL_RECEIVE_TIME_WAIT * 1000;
        while (sw.getTime() < timeInMilliseconds) {
            this.executeQuery();
            if (this.messages != null) break;

            Thread.sleep(3 * 1000);
        }
        sw.stop();

        return this;
    }

    @SneakyThrows
    public GmailHelper executeQuery() {
        String gmailUser = "me";
        Gmail.Users.Messages.List list = this.gmailService.users().messages().list(gmailUser).
            setMaxResults(MESSAGE_LIST_MAX_RESULTS).setQ(this.query);
        this.messages = list.execute().getMessages();

        return this;
    }

    public boolean doMessagesExist() {
        return this.messages != null;
    }

    public int countMessages() {
        return this.messages == null ? 0 : this.messages.size();
    }

    public static void main(String[] args) {
        String q = "from:{from} subject:\"{subject}\"";
        GmailHelper gmailHelper = new GmailHelper(q).executeQueryWithWaitingTime();
        Assert.assertTrue(
            gmailHelper.doMessagesExist(),
            String.format("There is no email sent to user in %s seconds", Constants.EMAIL_RECEIVE_TIME_WAIT)
        );
        Assert.assertEquals(
            gmailHelper.countMessages(), 1, "There are more than one email sent to user");
    }
}
