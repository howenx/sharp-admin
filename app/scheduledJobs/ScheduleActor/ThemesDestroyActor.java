package scheduledJobs.ScheduleActor;


import akka.actor.UntypedActor;
import com.fasterxml.jackson.annotation.JsonFormat;
import scheduledJobs.DBConn;
import java.sql.*;


/**
 * Created by tiffany on 15/12/25.
 */

public class ThemesDestroyActor extends UntypedActor{

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private final Timestamp now;
    public ThemesDestroyActor(Timestamp now) {
        this.now = now;
    }
    @Override
    public void onReceive(Object message) throws Exception {
        System.out.print("<<<<<<");

        System.out.print(this.now);

        /*
        Connection connection = DBConn.getConn("style");
        String updSql = "";
        if(connection != null) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(updSql);
            DBConn.colseConn(connection,statement,null);
        }
        */



    }
}
