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
        //final TextView status1 = (TextView) findViewById(R.id.status1);
        //final TextView status2 = (TextView) findViewById(R.id.status2);
        //final TextView status3 = (TextView) findViewById(R.id.status3);

        final View backToTopText = findViewById(R.id.back_to_top);
        final View backToTopArrow = findViewById(R.id.back_to_top_arrow);

        final View paralax =  findViewById(R.id.paralax);
        final View paralaxImage =  findViewById(R.id.paralax_image);

        final View loginHolder = findViewById(R.id.login_holder);
        final View welcomeImage = findViewById(R.id.welcome_logo);
        final View welcomeText = findViewById(R.id.welcome_text);

        View.OnClickListener jumpToTopListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToTopArrow.animate().alpha(0).setDuration(0).start();
                backToTopText.animate().alpha(0).setDuration(0).start();
                viewPager.setCurrentItem(1, true);
            }
        };
        backToTopText.setOnClickListener(jumpToTopListener);
        backToTopArrow.setOnClickListener(jumpToTopListener);

        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int poistion) {
                if(poistion == 0 || poistion == 4)
                    return new Fragment();

                return TourFragment.getInstance(poistion);
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
            public static final int LOGINHOLDER_STATUS_OUT = 1;
            public static final int LOGINHOLDER_STATUS_VISIBLE = 2;
            public int loginHolderStatus;
            public boolean welcomeShowed = false;
            public boolean mainHided = false;

            @Override
            public void onScroll(int y, float page) {
                //status3.setText("\ny: " + y + "\nyOffset: " + page);

                if (page < 1) {
                    float alpha = 1 - page * 2;
                    backToTopArrow.setAlpha(alpha);
                    //status1.setText("alpha:" + alpha);
                    if(loginHolder.getTop()!=0) {
                        float loginY = (float) ((float) 1 - (page));
                        loginHolder.animate().y(loginHolder.getTop() * loginY).setDuration(0).setStartDelay(0).start();
                        loginHolderStatus = LOGINHOLDER_STATUS_VISIBLE;
                        float welcomeImageY = ((float) 1.0 - (page * 4));
                        //welcomeImage.animate().y(welcomeImage.getTop() * welcomeImageY).setDuration(0).setStartDelay(0).start();

                        float welcomeTextY = (float) ((float) 1.0 - (page * 2.5));
                        if (alpha > 0) {
                            welcomeImage.animate().alpha(alpha).setDuration(0).start();
                            welcomeText.animate().alpha(alpha).setDuration(0).start();
                        } else {
                            welcomeImage.animate().alpha(0).setDuration(0).start();
                            welcomeText.animate().alpha(0).setDuration(0).start();
                        }
                    }
                    mainHided = false;
                }
                if (page>=1 && page <= 3) {
                    if(!mainHided) {
                        mainHided = true;
                        loginHolder.animate().y(0).setDuration(0).start();
                        welcomeImage.animate().alpha(0).setDuration(0).start();
                        welcomeText.animate().alpha(0).setDuration(0).start();
                        backToTopArrow.animate().alpha(0).setDuration(0).start();
                        backToTopText.animate().alpha(0).setDuration(0).start();
                    }
                }
                if (page > 3) {

                    float alpha = (page - 3) * 2 - 1;
                    backToTopText.animate().alpha(alpha).setDuration(0).start();
                    backToTopArrow.animate().alpha(alpha).setDuration(0).start();
                    float progress = page - 3;
                    //status1.setText("progress:" + progress);

                    if(progress>0.5){
                        if(loginHolderStatus == LOGINHOLDER_STATUS_VISIBLE) {
                            loginHolderStatus = LOGINHOLDER_STATUS_OUT;
                            loginHolder.animate().y( - loginHolder.getHeight()).setDuration(200).setStartDelay(0).start();
                        }
                        welcomeImage.animate().alpha(alpha*2-0.5f).setDuration(0).start();
                        /*if(progress>0.9) {
                            if (!welcomeShowed) {
                                welcomeShowed = true;

                                welcomeImage.setAlpha(1);
                                welcomeText.setAlpha(1);

                                AnimationSet imageAnimationSet = new AnimationSet(true);
                                imageAnimationSet.setDuration(300);
                                imageAnimationSet.setInterpolator(new AccelerateDecelerateInterpolator());
                                imageAnimationSet.addAnimation(new AlphaAnimation(0, 1));
                                imageAnimationSet.addAnimation(new ScaleAnimation(0.5f, 1, 0.5f, 1, Animation.RELATIVE_TO_SELF, (float) 0.5, Animation.RELATIVE_TO_SELF, 0.5f));
                                welcomeImage.startAnimation(imageAnimationSet);


                                AnimationSet textAnimationSet = new AnimationSet(true);
                                textAnimationSet.setDuration(300);
                                textAnimationSet.setStartOffset(250);
                                textAnimationSet.setInterpolator(new DecelerateInterpolator());
                                textAnimationSet.addAnimation(new AlphaAnimation(0, 1));
                                textAnimationSet.addAnimation(new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0));
                                welcomeText.startAnimation(textAnimationSet);
                            }
                        } else {
                            welcomeShowed = false;

                        }*/
                    }else{
                        if(loginHolderStatus== LOGINHOLDER_STATUS_OUT) {
                            loginHolderStatus = LOGINHOLDER_STATUS_VISIBLE;
                            loginHolder.animate().y(0).setDuration(200).setStartDelay(0).start();
                        }
                    }
                    mainHided= false;
                }

                //paralax.scrollTo(0, (int) (paralax.getHeight()*(page/10)));
                paralaxImage.animate().y(-(((float) paralax.getHeight()) * ((float) page / 10))).setDuration(0).setStartDelay(0).start();

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
