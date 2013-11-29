package pt.ulisboa.tecnico.bank.dao;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: Aliaksandra Sankova
 * Date: 11/29/13
 * Time: 3:09 AM
 */
public interface DAO<T> {

    public Long create (T item);

    public T read (Long id);

    public void update (T item);

    public void delete (T item);

    public void createOrUpdate(T item);

    public List<T> list();

    public List<T> list(int first, int count);

    public Long size();
}
