# Fragments
A Fragment is a class that contains a portion of an app's UI and behavior, which can be added as part of an Activity UI.

* A Fragment can be a static part of an Activity UI so that it remains on the screen during the entire lifecycle of the Activity, or it can be a dynamic part of the UI, added and removed while the Activity is running. For example, the Activity can include buttons to open and close the Fragment.

## The benefits of using fragments
* Reuse a fragment
* add or remove a fragment dynamically
* integrte a mini-UI into the activity
* retain the data instances after a configuration change
* represent section of an layout for different screen sizes

## Using a fragment
The general steps to use a Fragment:
1. Create a subclass of Fragment.
2. Create a layout for the Fragment.
3. Add the Fragment to a host Activity, either statically or dynamically.

### Creating a fragment
To create a Fragment in an app, extend the Fragment class, then override key lifecycle methods to insert your app logic, similar to the way you would with an Activity class.

Fragment Subclasses:
* DialgoFragment
* ListFragment
* PreferenceFragment

```java
public class SimpleFragment extends Fragment {

    public SimpleFragment() {
        // Required empty public constructor
    }
    // ...
}
```

### Creating a layout for a fragment
```java
@Override
public View onCreateView(LayoutInflater inflater, 
                         ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_simple, container, false);
}
```

* The container parameter passed to onCreateView() is the parent ViewGroup from the Activity layout. Android inserts the Fragment layout into this ViewGroup.

### Adding a fragment to an activity
There are 2 ways to include a fragment inside a activity layout:
1. Statically:  inside the XML layout file for the Activity

Declare the Fragment inside the layout file for the Activity (such as activity_main.xml) using the <fragment> tag. You can specify layout properties for the Fragment as if it were a View.

2. Dynamically: using fragment transactions.

To add a Fragment, your Activity code needs to specify a ViewGroup as a placeholder for the Fragment, such as a LinearLayout or a FrameLayout:
```xml
<FrameLayout
    android:id="@+id/fragment_container"
    android:name="SimpleFragment"
    android:layout_width="0dp"
    android:layout_height="wrap_content"
    app:layout_constraintLeft_toLeftOf="parent"
    app:layout_constraintRight_toRightOf="parent"
    app:layout_constraintTop_toTopOf="parent"
    tools:layout="@layout/fragment_simple">
</FrameLayout>
```
To manage a Fragment in your Activity, create an instance of the Fragment, and an instance of FragmentManager using getSupportFragmentManager(). With FragmentManager you can use FragmentTransaction methods to perform Fragment operations while the Activity runs.

Within the transaction you can:
* Add a Fragment using add().
* Remove a Fragment using remove().
* Replace a Fragment with another Fragment using replace().
* Hide and show a Fragment using hide() and show().

The best practice for instantiating the Fragment in the Activity is to provide a newinstance() factory method in the Fragment. (Factory Method option adds this method for us)

1. Open SimpleFragment, and add the following method to the end for instantiating the Fragment:
```java
 public static SimpleFragment newInstance() {
         return new SimpleFragment();
 }
```

2. In the Activity, instantiate the Fragment by calling the newInstance() method in SimpleFragment:
```java
 SimpleFragment fragment = SimpleFragment.newInstance();
```

3. In the Activity, get an instance of FragmentManager with getSupportFragmentManager().
```java
 FragmentManager fragmentManager = getSupportFragmentManager();
```

4. Use beginTransaction() with an instance of FragmentTransaction to start a series of edit operations on the Fragment:
```java
 FragmentTransaction fragmentTransaction = 
                                  fragmentManager.beginTransaction();
```
5. You can then add a Fragment using the add() method, and commit the transaction with commit(). For example:
```java
 fragmentTransaction.add(R.id.fragment_container, fragment);
 fragmentTransaction.commit()
```
The first argument passed to add() is the ViewGroup in which the fragment should be placed (specified by its resource ID fragment_container). The second parameter is the fragment to add.

The following shows a transaction that removes the Fragment simpleFragment using remove():
```java
// Get the FragmentManager.
FragmentManager fragmentManager = getSupportFragmentManager();
// Check to see if the fragment is already showing.
SimpleFragment simpleFragment = (SimpleFragment) fragmentManager
                                 .findFragmentById(R.id.fragment_container);
if (simpleFragment != null) {
    // Create and commit the transaction to remove the fragment.
    FragmentTransaction fragmentTransaction =
                                         fragmentManager.beginTransaction();
    fragmentTransaction.remove(simpleFragment).commit();
}
```
