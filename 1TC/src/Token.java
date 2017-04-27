public class Token {
    int tip;
    int val;
    public Token(){
    	
    }
    public Token(int t, int v)
    {
        tip = t;
        val = v;
    }

    public int gettip(){
        return tip;
    }

    public int getval() {
        return val;
    }
    public void settip(int i){
        this.tip = i;
    }

    public void setval(int i) {
        this.val=i;
    }
}