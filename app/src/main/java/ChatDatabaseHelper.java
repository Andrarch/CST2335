import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Andrew on 3/9/2018.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper {
    static String DATABASE_NAME="Messages.db";
    static int VERSION_NUMBER=1;
    public ChatDatabaseHelper(Context ctx) {
        super(ctx,DATABASE_NAME, null, VERSION_NUMBER);
    }
    private static String TABLE_NAME="MessageTable";
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "( " + "id"
            + " integer primary key autoincrement, " + "Message"
            + " text not null);";
    public void onCreate(SQLiteDatabase db){
        db.execSQL(DATABASE_CREATE);

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        onCreate(db);
    }
}
