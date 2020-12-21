package com.cj.musicoffline.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cj.musicoffline.model.AudioModel;
import com.cj.musicoffline.model.FavouriteModel;
import com.cj.musicoffline.model.PlayListModel;
import com.cj.musicoffline.model.SearchContent;
import com.cj.musicoffline.utils.PopulateDbAsync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {AudioModel.class, FavouriteModel.class, PlayListModel.class, SearchContent.class}, version = 5, exportSchema = false)
public abstract class MusicDatabase extends RoomDatabase {
    public abstract MusicDao musicDao();

    public abstract FavouriteDao favouriteDao();

    public abstract PlayListDao playlistDao();

    public abstract SearchDao searchDao();

    private static volatile MusicDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static MusicDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MusicDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MusicDatabase.class, "musicoffline_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };
}