package controllers;

import ExternalServices.TelegramApi;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.TeleramBotMessage;
import models.requestModels.TextMessageSendModel;
import play.Configuration;
import play.Logger;
import play.libs.ws.WSClient;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

/**
 * Created by yurabraiko on 08.05.17.
 */
public class Bot extends Controller {

    private final Configuration configuration;
    private WSClient wsClient;
    private TelegramApi telegramApi;


    @Inject
    public Bot(WSClient wsClient, Configuration configuration) {
        this.wsClient = wsClient;
        this.configuration = configuration;
        telegramApi = new TelegramApi(wsClient, configuration);
    }


    public Result onUpdate() {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode requestData = request().body().asJson();
        if (requestData != null) {
            Logger.error(request().body().asText());
            Logger.error(request().body().asJson().toString());
            requestData = requestData.get("message");
            TeleramBotMessage message = new TeleramBotMessage();
            message.message = requestData.get("text").asText();
            message.name = requestData.get("from").get("first_name").asText() + " " + requestData.get("from").get("last_name").asText();
            message.insert();

            telegramApi.sendMessage(new TextMessageSendModel(requestData.get("chat").get("id").asLong(), message.message));
        }
        return ok("Жив був пес");
    }
}
