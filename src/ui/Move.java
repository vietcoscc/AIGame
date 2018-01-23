package ui;

// for encoding moves
public class Move {
    int move = 0;
    public Move(int from, int to){
        move = ((from & 0x3f)) << 6 | (to & 0x3f);
    }

    public int getTo(){
        return move & 0x3f;
    }

    public int getFrom(){
        return  (move >> 6) & 0x3f;
    }


    public void setTo(int to){
        move &= ~0x3f;
        move|= to & 0x3f;
    }

    public void setFrom(int from){
        move &= ~0xfc0;
        move |= (from & 0x3f) << 6;
    }

}
