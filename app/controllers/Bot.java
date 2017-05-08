package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.TeleramBotMessage;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by yurabraiko on 08.05.17.
 */
public class Bot extends Controller {
    public Result onUpdate() {
        JsonNode requestData = request().body().asJson();
        if (requestData != null) {
            Logger.error(request().body().asText());
            Logger.error(request().body().asJson().toString());
            requestData = requestData.get("message");
            TeleramBotMessage message = new TeleramBotMessage();
            message.message = requestData.get("text").asText();
            message.name = requestData.get("from").get("first_name").asText() + " " + requestData.get("from").get("last_name").asText();
            message.insert();
        }
        return ok("Жив був пес");
    }
}
