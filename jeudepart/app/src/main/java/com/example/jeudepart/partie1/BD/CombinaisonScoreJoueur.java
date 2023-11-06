package com.example.jeudepart.partie1.BD;

public class CombinaisonScoreJoueur implements Comparable<CombinaisonScoreJoueur>{

    private Joueur joueur;

    private Score score;

    public CombinaisonScoreJoueur(Joueur joueur, Score score) {
        this.joueur = joueur;
        this.score = score;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    @Override
    public int compareTo(CombinaisonScoreJoueur combinaisonScoreJoueur) {
        return this.score.getScore()-combinaisonScoreJoueur.score.getScore();
    }
}
