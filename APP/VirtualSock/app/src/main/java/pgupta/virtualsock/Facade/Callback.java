package pgupta.virtualsock.Facade;

/**
 * Created by pulki on 10/14/2017.
 */

public interface Callback<T> {
    void accept(T t);
}
