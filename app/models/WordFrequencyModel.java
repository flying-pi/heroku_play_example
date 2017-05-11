package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import play.Play;
import uk.co.panaxiom.playjongo.PlayJongo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yurabraiko on 11.05.17.
 */
public class WordFrequencyModel {
    public static PlayJongo jongo = Play.application().injector().instanceOf(PlayJongo.class);

    public static MongoCollection wordFrequencyModel() {
        return jongo.getCollection("WordFrequencyModel");
    }


    static {
        Map<String,Integer> map = new HashMap<>();
        map.put("word",1);
        map.put("type",1);

        wordFrequencyModel().getDBCollection().createIndex(new BasicDBObject(map),null,true);
    }

    @JsonProperty("_id")
    public ObjectId id;

    public  int type;

    public String word;

    public double frequency;

    public WordFrequencyModel(String word,double frequency, int type){
        this.word = word;
        this.frequency = frequency;
        this.type = type;
    }

    public WordFrequencyModel insert() {
        wordFrequencyModel().save(this);
        return this;
    }
}
