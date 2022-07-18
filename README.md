# Hibernate pagination not working when using native queries (Named query exists but its result type is not compatible)

Also posted here: https://stackoverflow.com/questions/72859385/hibernate-pagination-not-working-when-using-native-queries-named-query-exists-b

## Description

I have a two named native queries in my orm.xml, one for retrieving the data, and one for doing the count for my pagination:

```
<named-native-query name="Person.findPeople.count">
    <query>select count(*)
        from person
    </query>
</named-native-query>

<named-native-query name="Person.findPeople">
    <query>select first_name, last_name
           from person
    </query>
</named-native-query>
```

To load this data, I have a Spring Data Repository, which loads a projection of the data (my actual code is more complex than the provided example):

```
@Query(nativeQuery = true)
fun findPeople(pageable: Pageable): Page<PersonFirstName>
```

Now, when I execute the above code, I'm getting an error:

```
Caused by: java.lang.IllegalArgumentException: Named query exists but its result type is not compatible
at org.hibernate.internal.AbstractSharedSessionContract.resultClassChecking(AbstractSharedSessionContract.java:984) ~[hibernate-core-5.6.9.Final.jar:5.6.9.Final]
at org.hibernate.internal.AbstractSharedSessionContract.createNativeQuery(AbstractSharedSessionContract.java:942) ~[hibernate-core-5.6.9.Final.jar:5.6.9.Final]
at org.hibernate.internal.AbstractSharedSessionContract.buildQueryFromName(AbstractSharedSessionContract.java:920) ~[hibernate-core-5.6.9.Final.jar:5.6.9.Final]
```

This is caused by Hibernate, which doesn't want to map the native count query to a Long. I've changed the named-native-query in my orm.xml to a named-query, and that does work, but I can't use that in my actual code.
 

