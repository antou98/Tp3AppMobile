package com.example.jeudepart.BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;


public class DatabaseManager extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "Jeu.db";
    private static final int DATABASE_VERSION = 2;

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    //créer les tables
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable( connectionSource, Joueur.class );

        }catch (Exception e){
            Log.i("Error DATABASE perso","Error creation db : "+e.getMessage().toString());
        }
    }

    //recréer les tables
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable( connectionSource, Joueur.class ,true);
            onCreate(database,connectionSource);
        }catch (Exception e){
            Log.i("Error DATABASE perso","Error creation db : "+e.getMessage().toString());
        }
    }

    //insert data joueur
    public void insertJoueur(Joueur joueur)  {
        try {
            Dao<Joueur,Integer> dao = getDao(Joueur.class);
            dao.create(joueur);
        }catch (Exception e){
            Log.i("Error DATABASE perso","Error insertion joueur table : "+e.getMessage().toString());
        }
    }

    //retourner les joueur
    public List<Joueur> getAllJoueurs() {
        try {
            Dao<Joueur, Integer> dao = getDao( Joueur.class );
            List<Joueur> scores = dao.queryForAll();
            return scores;
        } catch( Exception exception ) {
            Log.e( "DATABASE", "Error lire données table joueur table", exception );
            return null;
        }
    }
}
