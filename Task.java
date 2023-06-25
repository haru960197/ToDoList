public class Task {
    private String content;
    private boolean state;
    
    public String getContent() { return this.content; }
    public void setContent(String content) {
        if (content.equals("")) {
            throw new IllegalArgumentException
                ("タスクの内容が空文字列です");
        }
        this.content = content;
    }
    public boolean isDone() { return this.state; }
    public void setState(boolean state) { this.state = state; }
    
    public Task(String content) {
        this.setContent(content);
        this.state = false;
    }
}