package com.quandoo.restaurant.ui;


import android.content.res.Resources;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import com.quandoo.restaurant.R;
import com.quandoo.restaurant.ui.views.activity.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.quandoo.restaurant.sharedtest.EspressoUtils.nthChildOf;

/**
 * Created by Behzad on 1/7/2018.
 *
 * Run the Espresso tests individually, they would fail when running all of them together.
 * It seems it's a bug of Espresso.
 */

@RunWith(AndroidJUnit4.class)
public class customers_screen {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule
            = new ActivityTestRule<MainActivity>(MainActivity.class);

    Resources resources;

    @Before
    public void start(){
        resources = activityTestRule.getActivity().getResources();
    }

    @Test
    public void test_customer_item_click() {

        performClickAt(2);

        onView(withText(resources.getString(R.string.fragment_tables_title)));
    }

    @Test
    public void test_reservation_n_cancelling() {

        performClickAt(2);

        checkItemTextAt(10,"11");

        performClickAt(10);

        onView(withText(resources.getString(R.string.dialog_reservation_title)))
                .check(matches(isDisplayed()));

        onView(withText(resources.getString(R.string.dialog_yes)))
                .perform(click());

        performClickAt(10);

        onView(withText(resources.getString(R.string.dialog_cancel_reservation_title)))
                .check(matches(isDisplayed()));

        onView(withText(resources.getString(R.string.dialog_yes)))
                .perform(click());
    }

    @Test
    public void test_reservation_n_cancelling2() {

        performClickAt(2);

        checkItemTextAt(10,"11");

        performClickAt(10);

        onView(withText(resources.getString(R.string.dialog_reservation_title)))
                .check(matches(isDisplayed()));

        onView(withText(resources.getString(R.string.dialog_yes)))
                .perform(click());

        pressBack();

        performClickAt(2);

        onView(withText(resources.getString(R.string.dialog_cancel_reservation_title)))
                .check(matches(isDisplayed()));

        onView(withText(resources.getString(R.string.dialog_yes)))
                .perform(click());
    }

    @Test
    public void test_searchview(){
        onView(withId(R.id.searchView))
                .perform(click());

        onView(isAssignableFrom(EditText.class))
                .perform(typeText("mar"));

        checkItemTextAt(1,"Martin Luther King");
    }

    @Test
    public void test_cancelling_disabled_tables(){
        performClickAt(2);

        checkItemTextAt(0,"1");

        performClickAt(0);

        onView(withText(resources.getString(R.string.dialog_reservation_alert_title)))
                .check(matches(isDisplayed()));
    }


    private void checkItemTextAt(int index, String text){
        onView(nthChildOf(withId(R.id.recyclerView), index))
                .check(matches(hasDescendant(withText(text))));
    }

    private void performClickAt(int index){
        onView(withId(R.id.recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(index, click()));
    }


}
