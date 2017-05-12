package models.requestModels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yurabraiko on 09.05.17.
 */
public class TextMessageSendModel {
    public long chat_id;
    public String text;
    public Keyboard reply_markup;

    public TextMessageSendModel() {
        reply_markup = new Keyboard();
    }

    public TextMessageSendModel(long chat_id, String text) {
        this();
        this.chat_id = chat_id;
        this.text = text;
    }

    public class Keyboard {

        public List<List<String>> keyboard;

        public Keyboard() {
            keyboard = new ArrayList<>();
            List<String> firstLine = new ArrayList<>();
            firstLine.add("1 :: 1");
            firstLine.add("1 :: 2");
            keyboard.add(firstLine);
            List<String> secondLine = new ArrayList<>();
            secondLine.add("2 :: 1");
            secondLine.add("2 :: 1");
            keyboard.add(secondLine);
        }
    }
}
