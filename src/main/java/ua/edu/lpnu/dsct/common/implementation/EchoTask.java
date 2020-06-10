package ua.edu.lpnu.dsct.common.implementation;

import ua.edu.lpnu.dsct.common.abstraction.ITask;

import java.io.Serializable;

public class EchoTask implements ITask<String>, Serializable {
    private String text;

    public EchoTask(String text) {
        this.text = text;
    }

    @Override
    public String execute() {
        return text;
    }
}
