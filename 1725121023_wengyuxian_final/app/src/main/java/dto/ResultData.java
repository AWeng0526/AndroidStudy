package dto;

// 解析返回的json需要的类结构
public class ResultData {
    String from;
    String to;
    String[] transResult;
    String src;
    String dst;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String[] getTransResult() {
        return transResult;
    }

    public void setTransResult(String[] transResult) {
        this.transResult = transResult;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }
}
