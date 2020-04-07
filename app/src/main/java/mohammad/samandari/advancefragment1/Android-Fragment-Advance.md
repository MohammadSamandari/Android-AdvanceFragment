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

# Fragment lifecycle and communications
Each lifecycle callback for the Activity results in a similar callback for each Fragment, as shown in the following table. For example, when the Activity receives onPause(), it triggers a Fragment onPause() for each Fragment in the Activity.

As with an Activity, you can save the variable assignments in a Fragment. Because data in a Fragment is usually relevant to the Activity that hosts it, your Activity code can use a callback to retrieve data from the Fragment, and then restore that data when recreating the Fragment. You learn more about communicating between an Activity and a Fragment later in this chapter.

For a complete description of all Fragment lifecycle callbacks, see Fragment.

https://developer.android.com/reference/android/app/Fragment.html

## Using Fragment methods and the Activity context
An Activity can use methods in a Fragment by first acquiring a reference to the Fragment. Likewise, a Fragment can get a reference to its hosting Activity to access resources, such as a View.

### Using the Activity context in a Fragment
When a Fragment is in the active or resumed state, it can get a reference to its hosting Activity instance using getActivity(). It can also perform tasks such as finding a View in the Activity layout:
```java
View listView = getActivity().findViewById(R.id.list);
```
* Note that if you call getActivity() when the Fragment is not attached to an Activity, getActivity() returns null.

### Using the Fragment methods in the host Activity
Likewise, your Activity can call methods in the Fragment by acquiring a reference to the Fragment from FragmentManager, using findFragmentById(). For example, to call the getSomeData() method in the Fragment, acquire a reference first:
```java
ExampleFragment fragment = (ExampleFragment)
                getFragmentManager().findFragmentById(R.id.example_fragment);
// ...
mData = fragment.getSomeData();
```
### Adding the Fragment to the back stack
For a Fragment, the hosting Activity maintains a back stack, and you have to explicitly add a Fragment to that back stack by calling addToBackStack() during any transaction that adds the Fragment.
```java
fragmentTransaction.add(R.id.fragment_container, fragment);
fragmentTransaction.addToBackStack(null);
fragmentTransaction.commit();
```

### Sending data to a Fragment from an Activity
To send data to a Fragment from an Activity, set a Bundle and use the Fragment method setArguments(Bundle) to supply the construction arguments for the Fragment.

### Sending data from a Fragment to its host Activity
To have a Fragment communicate to its host Activity, follow these steps in the Fragment:
* Define a listener interface, with one or more callback methods to communicate with the Activity.
* Override the onAttach() lifecycle method to make sure the host Activity implements the interface.
* Call the interface callback method to pass data as a parameter.

In the host Activity, follow these steps:
* Implement the interface defined in the Fragment. (All the Activity classes that use the Fragment have to implement the interface.)
* Implement the Fragment callback method(s) to retrieve the data.

Some data in a Fragment is may be relevant to the Activity that hosts it. Your Activity code can use a callback to retrieve relevant data from the Fragment. The Activity can then send that data to the Fragment when recreating the Fragment.
```java
// Interface definition and onFeedbackChoice() callback.
interface OnFragmentInteractionListener {
    void onRadioButtonChoice(int choice);
}

@Override
public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnFragmentInteractionListener) {
        mListener = (OnFragmentInteractionListener) context;
    } else {
        throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
    }
}
```
In Activity:
```java
public class MainActivity extends AppCompatActivity
                implements SimpleFragment.OnFragmentInteractionListener {
   //...
}
```





