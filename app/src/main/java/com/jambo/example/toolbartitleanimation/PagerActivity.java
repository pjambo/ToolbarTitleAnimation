package com.jambo.example.toolbartitleanimation;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.jambo.example.toolbartitleanimation.ui.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JAMBO on 7/14/15.
 */

public class PagerActivity extends AppCompatActivity {

    //This represents the toolbar title
    private TextSwitcher mSwitcher;

    ViewPager viewPager;
    CircleIndicator defaultIndicator;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        /**
         * https://snowdog.co/blog/how-to-dynamicaly-change-android-toolbar-icons-color
         */
        //ToolbarColorizeHelper.colorizeToolbar(toolbar, R.color.white, this);

        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);

        mSwitcher = (TextSwitcher) findViewById(R.id.textSwitcher);
        mSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            public View makeView() {
                TextView myText = new TextView(PagerActivity.this);
                myText.setGravity(Gravity.START);
                myText.setTextAppearance(PagerActivity.this, R.style.TitleTextApperance);
                return myText;
            }
        });

        /**
         * Set IN an OUT animation for the {@link ToolBar} title
         * ({@link TextSwitcher} in this case) when pager is swiped to the left
         */
        final Animation IN_SWIPE_BACKWARD = AnimationUtils.loadAnimation(this, R.anim.slide_in_top);
        final Animation OUT_SWIPE_BACKWARD = AnimationUtils.loadAnimation(this, R.anim.slide_out_bottom);

        /**
         * Set IN an OUT animation for the {@link ToolBar} title
         * ({@link TextSwitcher} in this case) when pager is swiped to the right
         */
        final Animation IN_SWIPE_FORWARD = AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom);
        final Animation OUT_SWIPE_FORWARD = AnimationUtils.loadAnimation(this, R.anim.slide_out_top);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        defaultIndicator = (CircleIndicator) findViewById(R.id.indicator_default);

        //Retrieve last selected page from preferences of return 0 by default
        final SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        final int currentItem = sharedPrefs.getInt("position", 0);

        if (viewPager != null) {
            final Adapter adapter = setupViewPager(viewPager);

            //Set pager to last selected page and toolbar title to the corresponding title
            viewPager.setCurrentItem(currentItem);
            defaultIndicator.setViewPager(viewPager);
            mSwitcher.setText(adapter.getPageTitle(currentItem));

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    //Retrieve previous pager position to determine swipe direction
                    int prevPosition = sharedPrefs.getInt("position", 0);

                    //Set TextSwitcher animation based on swipe direction
                    if (position >= prevPosition) {
                        mSwitcher.setInAnimation(IN_SWIPE_FORWARD);
                        mSwitcher.setOutAnimation(OUT_SWIPE_FORWARD);
                    } else {
                        mSwitcher.setInAnimation(IN_SWIPE_BACKWARD);
                        mSwitcher.setOutAnimation(OUT_SWIPE_BACKWARD);
                    }
                    mSwitcher.setText(adapter.getPageTitle(position));
                    setBackgroundColor(position, prevPosition);

                    //Store current position in SharedPreferences
                    sharedPrefs.edit().putInt("position", position).apply();
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "I love sport!", Snackbar.LENGTH_LONG)
                        .setAction("Dismiss", null).show();
            }
        });

    }

    /**
     * Use this method to chande the background color
     * of the {@link Toolbar} and the {@link CircleIndicator}
     *
     * @param i Current Pager Position.
     * @param j Previous Pager Position.
     */
    private void setBackgroundColor(int i, int j) {

        //Demo background colors
        ArrayList<Colors> COLORS = new ArrayList<>();
        COLORS.add(new Colors(R.color.md_blue_500, R.color.md_blue_900));
        COLORS.add(new Colors(R.color.md_purple_500, R.color.md_purple_900));
        COLORS.add(new Colors(R.color.md_deep_purple_500, R.color.md_deep_purple_900));
        COLORS.add(new Colors(R.color.md_indigo_500, R.color.md_indigo_900));
        COLORS.add(new Colors(R.color.md_light_blue_500, R.color.md_light_blue_900));
        COLORS.add(new Colors(R.color.md_cyan_500, R.color.md_cyan_900));


        /**
         * Just change the background color
         */
        toolbar.setBackgroundColor(getResources().getColor(COLORS.get(i).getPrimaryLight()));
        defaultIndicator.setBackgroundColor(getResources().getColor(COLORS.get(i).getPrimaryLight()));

        /**
         * Animate between the previous backgound color and the next color
         * to produce a smoother transition between the two colors
         *
         * TODO: Still a little buggy!
         */
        /*ObjectAnimator coloFadeToolbar = ObjectAnimator.ofObject(toolbar, "backgroundColor", new ArgbEvaluator(), COLORS.get(j).getPrimaryLight(), COLORS.get(i).getPrimaryLight());
        ObjectAnimator coloFadeCircleIndicator = ObjectAnimator.ofObject(defaultIndicator, "backgroundColor", new ArgbEvaluator(), COLORS.get(j).getPrimaryLight(), COLORS.get(i).getPrimaryLight());
        coloFadeToolbar.setDuration(2000);
        coloFadeCircleIndicator.setDuration(2000);
        coloFadeToolbar.start();
        coloFadeCircleIndicator.start();*/

        /**
         *Change StatusBarColor in Lollipop and above
         */
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(0x80000000);
            window.setStatusBarColor(getResources().getColor(COLORS.get(i).getPrimaryDark()));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private Adapter setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(ContentFragment.newInstance("Football Content"), "Football");
        adapter.addFragment(ContentFragment.newInstance("Cricket Content"), "Cricket");
        adapter.addFragment(ContentFragment.newInstance("Hockey Content"), "Hockey");
        adapter.addFragment(ContentFragment.newInstance("Tennis Content"), "Tennis");
        adapter.addFragment(ContentFragment.newInstance("Volleyball Content"), "Volleyball");
        adapter.addFragment(ContentFragment.newInstance("Baseball Content"), "Baseball");
        viewPager.setAdapter(adapter);
        return adapter;
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
