/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils.misc;

import java.io.Serializable;

/**
 * @author Moldovanus
 */
public class Pair<T, S> implements Serializable {
    private T first;
    private S second;

    public Pair(T first, S second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public S getSecond() {
        return second;
    }

    public void setSecond(S second) {
        this.second = second;
    }


}