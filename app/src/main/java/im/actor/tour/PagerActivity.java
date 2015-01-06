package im.actor.tour;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class PagerActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);


        final VerticalViewPager viewPager = (VerticalViewPager) findViewById(R.id.viewpager);
        final TextView status1 = (TextView) findViewById(R.id.status1);
        final TextView status2 = (TextView) findViewById(R.id.status2);
        final TextView status3 = (TextView) findViewById(R.id.status3);

        final View firstContent = findViewById(R.id.first_content);
        final View lastContent = findViewById(R.id.last_content);



        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                if(i == 0 || i == 4)
                    return new Fragment();
                return new TourFragment();
            }

            @Override
            public int getCount() {
                return 5;
            }
        });


        //Necessary or the pager will only have one extra page to show
        // make this at least however many pages you can see
        viewPager.setOffscreenPageLimit(3);
        //A little space between pages
        viewPager.setPageMargin((int) getResources().getDimension(R.dimen.page_margin));
        //If hardware acceleration is enabled, you should also remove
        // clipping on the pager for its children.
        viewPager.setClipChildren(false);

        viewPager.setOnScrollListener(new ScrollListener() {
            @Override
            public void onScroll(int y, float page) {
                status3.setText("\ny: " + y + "\nyOffset: " + page);

                if (page < 0.5) {

                    float alpha = 1 - page * 2;
                    firstContent.setAlpha(alpha);
                    status1.setText("alpha:" + alpha);

                }

                if (page > 3.5) {

                    float alpha = (page - 3) * 2 - 1;
                    lastContent.setAlpha(alpha);
                    status1.setText("alpha:" + alpha);

                }



            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
