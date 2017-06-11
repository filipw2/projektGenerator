package com.example.projekt.generator;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;
import java.security.SecureRandom;
import java.math.BigInteger;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.projekt.generator", appContext.getPackageName());
    }

    @Rule
    public final ActivityTestRule<MainActivity> main = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testAdding(){
        int size = main.getActivity().editTextList.size();
        onView(withId(R.id.addButton)).perform(click());

        assertEquals(main.getActivity().editTextList.size(), size+1);
    }

    @Test
    public void testTextInput(){
        List<EditText> editTextList = main.getActivity().editTextList;

        String text = "test";

        onView(withId(R.id.addButton)).perform(click());
        EditText et = editTextList.get(editTextList.size()-1);

        onView(withId(et.getId())).perform(typeText(text), ViewActions.closeSoftKeyboard());

        onView(withId( et.getId())).perform(click());

        onView(withId( et.getId())).check(matches(withText(text)));

    }

    @Test
    public void testGenerating(){
        int passLength=5;
        SecureRandom random = new SecureRandom();
        for (int i=0; i<100; i++) {
            Generator.getInstance().clear();
            String generatedS = new BigInteger(130, random).toString(32);
            Generator.getInstance().insert(generatedS);
            Generator.getInstance().generate(passLength);
            String t = (String) Generator.getInstance().getPassword().get(0);
            assertEquals(t.length(), passLength);
            passLength++;
        }
    }


}
