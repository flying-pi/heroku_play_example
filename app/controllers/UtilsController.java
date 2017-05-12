package controllers;

import Logic.GlobalWordImporte;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by yurabraiko on 11.05.17.
 */
public class UtilsController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result importFromLoaclFile(String filePath) {
        GlobalWordImporte importer = new GlobalWordImporte();
        importer.importFromFile(filePath);
        importer.saveToBd();
        return ok("Жив був пес");
    }

    public Result analyzeText(String filePath){
        return ok("Жив був пес");
    }
}