package com.example.jeudepart.BD;

import com.example.jeudepart.BD.Joueur;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.Date;
@DatabaseTable( tableName = "T_Scores" )
public class Score {
    @DatabaseField( columnName = "idScore", generatedId = true )
    private int idScore;
    @DatabaseField( canBeNull = false, foreign = true, foreignColumnName = "idJoueur", foreignAutoCreate = true )
    private Joueur user;
    @DatabaseField
    private int score;
    @DatabaseField
    private Date when;

    public Score() {
    }

    public Score(Joueur user, int score, Date when) {
        this.user = user;
        this.score = score;
        this.when = when;
    }

    public int getIdScore() {
        return idScore;
    }

    public void setIdScore(int idScore) {
        this.idScore = idScore;
    }

    public Joueur getUser() {
        return user;
    }

    public void setUser(Joueur user) {
        this.user = user;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getWhen() {
        return when;
    }

    public void setWhen(Date when) {
        this.when = when;
    }

    @Override
    public String toString() {
        return idScore + ": " + user + " -> " + score + " : " + when.toString();
    }
}