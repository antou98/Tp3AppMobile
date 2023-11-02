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

            TableUtils.createTable( connectionSource, Score.class );
            TableUtils.createTable( connectionSource, Joueur.class );
        }catch (Exception e){
            Log.i("Error DATABASE perso","Error creation db : "+e.getMessage().toString());
        }
    }

    //recréer les tables
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable( connectionSource, Score.class, true );
            TableUtils.dropTable( connectionSource, Joueur.class ,true);
            onCreate(database,connectionSource);
        }catch (Exception e){
            Log.i("Error DATABASE perso","Error creation db : "+e.getMessage().toString());
        }
    }

    public void recreateTables(){
        try {
            TableUtils.dropTable( connectionSource, Score.class, true );
            TableUtils.dropTable( connectionSource, Joueur.class ,true);
            TableUtils.createTable( connectionSource, Joueur.class );
            TableUtils.createTable( connectionSource, Score.class );
        }catch (Exception e){

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

    public Joueur getJoueur(int id){
        try {
            Dao<Joueur, Integer> dao = getDao( Joueur.class );
            Joueur joueur = dao.queryForId(id);
            return joueur;
        }catch (Exception e){
            Log.e( "DATABASE", "Error lire données table joueur table", e );
            return null;
        }
    }

    public void insertScore( Score score ) {
        try {
            Dao<Score, Integer> dao = getDao( Score.class );
            dao.create( score );
            Log.i( "DATABASE", "insertScore invoked" );
        } catch( Exception exception ) {
            Log.e( "DATABASE", "Can't insert score into Database", exception );
        }
    }


    public List<Score> readScores() {
        try {
            Dao<Score, Integer> dao = getDao( Score.class );
            List<Score> scores = dao.queryForAll();
            Log.i( "DATABASE", "readScores invoked" );
            return scores;
        } catch( Exception exception ) {
            Log.e( "DATABASE", "Can't insert score into Database", exception );
            return null;
        }
    }

    public void deleteScore(int id){
        try {
            Dao<Score, Integer> dao = getDao( Score.class );
            dao.deleteById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void replaceScoreIfSmaller(Score scoreNew,Joueur joueur){
        try {
            Dao<Score, Integer> dao = getDao( Score.class );

            Score scoreOld = null;
            for (Score s:readScores()) {
                if (s.getUser().getIdJoueur()==joueur.getIdJoueur()){
                    scoreOld = s;
                }
            }

            if (scoreOld!=null){
                if(scoreOld.getScore()>=scoreNew.getScore()){
                    scoreOld.setScore(scoreNew.getScore());
                    dao.update(scoreOld);
                    Log.i("DATABASE","Score changed");
                }
            }else {
                dao.createIfNotExists(scoreNew);
                //Log.i("DATABASE","Score not changed");
            }

        } catch( Exception exception ) {
            Log.e( "DATABASE", "Can't score score into Database", exception );
        }
    }

    public Score getScoreFromJoueur(Joueur joueur){
        Score scoreRet = null;
        for (Score s:readScores()) {
            if (s.getUser().getIdJoueur()==joueur.getIdJoueur()){
                scoreRet = s;
                break;
            }
        }

        return scoreRet;
    }
}
