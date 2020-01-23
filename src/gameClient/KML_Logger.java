package gameClient;
import utils.Point3D;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
/**
 * This class creates KML file for each game, which documents the game and the moves.
 * It is possible to open the kml file in google earth and see it on it real location.
 */
public class KML_Logger {

    private int stage;
    private StringBuilder info;

    /**
     * Constructor to init the object with the begin of kml file
     * @param stage
     */

    public KML_Logger(int stage) {
        this.stage = stage;
        info = new StringBuilder();
        kmlStart();
    }

    /**
     * The opening text of kml file.
     * sets the elements in the game.
     */
    public void kmlStart()
    {
        info.append(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
                        "<kml xmlns=\"http://earth.google.com/kml/2.2\">\r\n" +
                        "  <Document>\r\n" +
                        "    <name>" + "Game stage :"+this.stage + "</name>" +"\r\n"+
                        " <Style id=\"node\">\r\n" +
                        "      <IconStyle>\r\n" +
                        "        <Icon>\r\n" +
                        "          <href>http://maps.google.com/mapfiles/kml/pal3/icon57.png</href>\r\n" +
                        "        </Icon>\r\n" +
                        "        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" +
                        "      </IconStyle>\r\n" +
                        "    </Style>" +
                        " <Style id=\"fruit-prisoner\">\r\n" +
                        "      <IconStyle>\r\n" +
                        "        <Icon>\r\n" +
                        "          <href>https://i.imgur.com/JKlsJmZ.png</href>\r\n" +
                        "        </Icon>\r\n" +
                        "        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" +
                        "      </IconStyle>\r\n" +
                        "    </Style>" +
                        " <Style id=\"fruit-criminal\">\r\n" +
                        "      <IconStyle>\r\n" +
                        "        <Icon>\r\n" +
                        "          <href>https://i.imgur.com/oYbZgYg.png</href>\r\n" +
                        "        </Icon>\r\n" +
                        "        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" +
                        "      </IconStyle>\r\n" +
                        "    </Style>" +
                        " <Style id=\"robot\">\r\n" +
                        "      <IconStyle>\r\n" +
                        "        <Icon>\r\n" +
                        "          <href>https://i.imgur.com/T4yl22i.png></href>\r\n" +
                        "        </Icon>\r\n" +
                        "        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" +
                        "      </IconStyle>\r\n" +
                        "    </Style>"
        );
    }

    /**
     * This function adds placemark to the kml file.
     * each object\element in the game has place mark.
     * each vetex of the graph and edge has place mark.
     * @param id
     * @param location
     */
    public void addPlaceMark(String id, String location) {
        LocalDateTime time = LocalDateTime.now();
        info.append(
                "    <Placemark>\r\n" +
                        "      <TimeStamp>\r\n" +
                        "        <when>" + time+ "</when>\r\n" +
                        "      </TimeStamp>\r\n" +
                        "      <styleUrl>#" + id + "</styleUrl>\r\n" +
                        "      <Point>\r\n" +
                        "        <coordinates>" + location + "</coordinates>\r\n" +
                        "      </Point>\r\n" +
                        "    </Placemark>\r\n"
        );
    }
    public void addEdgePlacemark(Point3D src, Point3D dest) {

        info.append("<Placemark>\r\n" +
                "<LineString>\r\n" +
                "<extrude>5</extrude>\r\n" +
                "<altitudeMode>clampToGround</altitudeMode>\r\n" +
                "<coordinates>\r\n" +
                src.toString()+"\r\n" +
                dest.toString()+"\r\n" +
                "</coordinates>" +
                "</LineString></Placemark>"
        );

    }

    /**
     * The closing text of the kml file.
     * This function creates the kml file and calling it by it stage name.
     * The file is in data folder.
     */
    public void kmlEnd()
    {
        info.append("  \r\n</Document>\r\n" +
                "</kml>"
        );
        try
        {
            File file=new File("data/"+stage+".kml");
            PrintWriter p=new PrintWriter(file);
            p.write(info.toString());
            p.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public String toString(){
        return info.toString();
    }

}