package com.bae.dialogflowbot.helpers;

import android.os.AsyncTask;
import android.util.Log;

import com.bae.dialogflowbot.MainActivity;
import com.bae.dialogflowbot.interfaces.BotReply;
import com.google.cloud.dialogflow.v2.DetectIntentRequest;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;

public class SendMessageInBg extends AsyncTask<Void, Void, DetectIntentResponse> {


  private final SessionName session;
  private final SessionsClient sessionsClient;
  private final QueryInput queryInput;
  private String TAG = "async";
  private BotReply botReply;

  public SendMessageInBg(MainActivity botReply, SessionName session, SessionsClient sessionsClient,
                         QueryInput queryInput) {
    this.botReply = botReply;
    this.session = session;
    this.sessionsClient = sessionsClient;
    this.queryInput = queryInput;
  }

  @Override
  protected DetectIntentResponse doInBackground(Void... voids) {
    try {
      DetectIntentRequest detectIntentRequest =
          DetectIntentRequest.newBuilder()
              .setSession(session.toString())
              .setQueryInput(queryInput)
              .build();
      return sessionsClient.detectIntent(detectIntentRequest);
    } catch (Exception e) {
      Log.d(TAG, "doInBackground: " + e.getMessage());
      e.printStackTrace();
    }
    return null;
  }

  @Override
  protected void onPostExecute(DetectIntentResponse response) {
    //handle return response here
    botReply.callback(response);
  }
}