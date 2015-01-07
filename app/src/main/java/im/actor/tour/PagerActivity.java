package im.actor.tour;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;


public class PagerActivity extends ActionBarActivity {

    private static final int SIGNIN = 1;
    private static final int SIGNIN_LAST = 2;
    private static final int SIGNUP = 3;
    private static final int SIGNUP_LAST = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        final View lastPage = findViewById(R.id.last_page);
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

        View signinView = findViewById(R.id.signin);
        View signupView = findViewById(R.id.signup);
        View signinLastView = findViewById(R.id.signin_last);
        View signupLastView = findViewById(R.id.signup_last);

        signinView.setOnClickListener(new LoginClickListener(SIGNIN));
        signinLastView.setOnClickListener(new LoginClickListener(SIGNIN_LAST));
        signupView.setOnClickListener(new LoginClickListener(SIGNUP));
        signupLastView.setOnClickListener(new LoginClickListener(SIGNUP_LAST));


        View.OnClickListener jumpToTopListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToTopArrow.animate().alpha(0).setDuration(0).start();
                backToTopText.animate().alpha(0).setDuration(0).start();
                lastPage.animate().alpha(0).setDuration(0).start();
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
            public boolean mainContentHidden = false;

            @Override
            public void onScroll(int y, float page) {
                //status3.setText("\ny: " + y + "\nyOffset: " + page);

                if (page < 1) {
                    float alpha = 1 - page * 2;
                    backToTopArrow.setAlpha(alpha);
                    //status1.setText("alpha:" + alpha);
                    if (loginHolder.getTop() != 0) {
                        float loginY = (float) ((float) 1 - (page));
                        loginHolder.animate().y(loginHolder.getTop() * loginY).setDuration(0).setStartDelay(0).start();
                        loginHolderStatus = LOGINHOLDER_STATUS_VISIBLE;
                        float welcomeImageY = welcomeImage.getTop()-y/2;
                        float welcomeTextY = welcomeText.getTop()-y/2;
                        if (alpha > 0) {
                            welcomeImage.animate().alpha(alpha).y(welcomeImageY).setDuration(0).start();
                            welcomeText.animate().alpha(alpha).y(welcomeTextY).setDuration(0).start();
                        } else {
                            welcomeImage.animate().alpha(0).setDuration(0).start();
                            welcomeText.animate().alpha(0).setDuration(0).start();
                        }
                    }
                   mainContentHidden = false;
                }
                if (page >= 1 && page <= 3) {
                    if (!mainContentHidden) {
                        mainContentHidden = true;
                        loginHolder.animate().y(0).setDuration(0).start();
                        welcomeImage.animate().alpha(0).setDuration(0).start();
                        welcomeText.animate().alpha(0).setDuration(0).start();
                        backToTopArrow.animate().alpha(0).setDuration(0).start();
                        backToTopText.animate().alpha(0).setDuration(0).start();
                        lastPage.animate().alpha(0).setDuration(0).start();
                    }
                }
                if (page > 3) {
                    float progress = page - 3;
                    float alpha = (page - 3) * 2 - 1;
                    if (alpha > 0) {
                        backToTopText.animate().alpha(alpha).setDuration(0).start();
                        backToTopArrow.animate().alpha(alpha).setDuration(0).start();
                    }
                    float welcomeImageScale = ((progress)/(4) +0.8f); //kinda delay
                    if (welcomeImageScale > 1) {
                        welcomeImageScale = 1;
                    }
                    //status1.setText("progress:" + progress);

                    if (progress > 0.5) {
                        if (loginHolderStatus == LOGINHOLDER_STATUS_VISIBLE) {
                            loginHolderStatus = LOGINHOLDER_STATUS_OUT;
                            loginHolder.animate().y(-loginHolder.getHeight()).setDuration(200).setInterpolator(new AccelerateDecelerateInterpolator()).setStartDelay(0).start();
                        }
                        float halfprogress = progress * 2 - 1;
                        if (halfprogress < 0) {
                            halfprogress = 0;
                        }

                        mainContentHidden = false;
                        lastPage.animate()
                                //.scaleX(welcomeImageScale).scaleY(welcomeImageScale)
                                .y(lastPage.getHeight()/2*((1-halfprogress))).alpha(halfprogress).setDuration(0).start();



                        //welcomeImage.animate().alpha(welcomeImageAlpha).setDuration(0).start();

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
                    } else {
                        if (loginHolderStatus == LOGINHOLDER_STATUS_OUT) {
                            loginHolderStatus = LOGINHOLDER_STATUS_VISIBLE;
                            loginHolder.animate().y(0).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(200).setStartDelay(0).start();
                        }
                        if(!mainContentHidden){
                            lastPage.animate().alpha(0).setDuration(0).start();
                        }
                    }
                }

                //paralax.scrollTo(0, (int) (paralax.getHeight()*(page/10)));
                paralaxImage.animate().y(-(((float) paralax.getHeight()) * ((float) page / 10))).setDuration(0).setStartDelay(0).start();

            }
        });

    }


    private class LoginClickListener implements View.OnClickListener {
        private final int key;

        public LoginClickListener(int key) {
            this.key = key;
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(PagerActivity.this, "Clicked key: " + key, Toast.LENGTH_SHORT).show();
        }
    }
}
