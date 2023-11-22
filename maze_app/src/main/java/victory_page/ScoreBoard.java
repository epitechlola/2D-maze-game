package victory_page;

public class ScoreBoard {
    private String name;
    private int score;

    public ScoreBoard(String name, int score){
        this.name=name;
        this.score=score;
    }

    public int getScore(){
        return this.score;
    }
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setScore(int score){
        this.score=score;
    }
}
