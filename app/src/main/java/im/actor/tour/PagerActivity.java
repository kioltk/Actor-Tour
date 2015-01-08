package im.actor.tour;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;


public class PagerActivity extends ActionBarActivity {

    private static final int SIGNIN = 1;
    private static final int SIGNIN_LAST = 2;
    private static final int SIGNUP = 3;
    private static final int SIGNUP_LAST = 4;
    private int lastPageIndex = 4;

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
        final View background = findViewById(R.id.background);

        final View loginHolder = findViewById(R.id.login_holder);
        //final View loginHolderBackground = findViewById(R.id.login_holder_background);
        final View welcomeImage = findViewById(R.id.welcome_logo);
        final View welcomeText = findViewById(R.id.welcome_text);

        final View signinView = findViewById(R.id.signin);
        final View signupView = findViewById(R.id.signup);
        final View signinLastView = findViewById(R.id.signin_last);
        final View signupLastView = findViewById(R.id.signup_last);

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
            public Fragment getItem(int position) {
                if(position == 0 || position == lastPageIndex + 1)
                    return new Fragment();

                return TourFragment.getInstance(position);
            }

            @Override
            public int getCount() {
                return 5;//6;
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
            public boolean loginHolderBackgroundVisible = false;

            @Override
            public void onScroll(int y, float page) {

                if (page < 1) {
                    float alpha = 1 - page * 2;
                    backToTopArrow.animate().alpha(alpha).setDuration(0).start();
                    //status1.setText("alpha:" + alpha);
                    if (loginHolder.getTop() != 0) {
                        float loginY = (float) loginHolder.getTop()*((float) 1 - (page));
                        loginHolder.animate().y(loginY).setDuration(0).setStartDelay(0).start();
                        background.animate().y(loginY-background.getHeight()+loginHolder.getHeight()).setDuration(0).start();
                        /*if(loginHolderBackgroundVisible){
                            loginHolderBackgroundVisible = false;
                            loginHolderBackground.animate().alpha(0).setDuration(0).start();
                        } else {

                        }*/
                        //loginHolderBackground.animate().alpha(page).setDuration(0).start();
                        background.animate().alpha(page).setDuration(0).start();
                        loginHolderStatus = LOGINHOLDER_STATUS_VISIBLE;
                        float welcomeImageY = welcomeImage.getTop()-y/2;
                        float welcomeTextY = welcomeText.getTop()-y/2;
                        if (alpha > 0) {
                            welcomeImage.animate()
                                    //.scaleX(alpha/2+0.5f).scaleY(alpha/2+0.5f)
                                    .alpha(alpha).y(welcomeImageY).setDuration(0).start();
                            welcomeText.animate()
                                    //.scaleX(alpha/2+0.5f).scaleY(alpha/2+0.5f)
                                    .alpha(alpha).y(welcomeTextY).setDuration(0).start();
                        } else {
                            welcomeImage.animate().alpha(0).setDuration(0).start();
                            welcomeText.animate().alpha(0).setDuration(0).start();
                        }
                    }
                   mainContentHidden = false;
                }else {
                    if (page >= 1 && page <= lastPageIndex) {
                        // todo freeze?
                        if (!mainContentHidden) {
                            mainContentHidden = true;
                            loginHolderBackgroundVisible = true;
                            //signinLastView.setEnabled(false);
                            //signinView.setEnabled(false);
                            //signupLastView.setEnabled(false);
                            //signupView.setEnabled(false);
                            loginHolder.animate().y(0).setDuration(0).start();
                            //loginHolderBackground.animate().alpha(1).setDuration(0).start();
                            background.animate().y(-loginHolder.getTop()).alpha(1).setDuration(0).start();

                            welcomeImage.animate().alpha(0).setDuration(0).start();
                            welcomeText.animate().alpha(0).setDuration(0).start();
                            backToTopArrow.animate().alpha(0).setDuration(0).start();
                            backToTopText.animate().alpha(0).setDuration(0).start();
                            lastPage.animate().alpha(0).setDuration(0).start();

                        }
                        if (page > lastPageIndex-1) {
                            float alpha = (page - (lastPageIndex-1)) * 3 - 2;
                            if (alpha> 0) {
                                backToTopText.animate().scaleX(alpha).scaleY(alpha).alpha(alpha).setDuration(0).start();
                                //backToTopArrow.animate().alpha(alpha).setDuration(0).start();
                            }else{
                                backToTopText.animate().scaleX(0).scaleY(0).alpha(0).setDuration(0).start();

                            }
                        }
                    } else {
                        if (page > lastPageIndex) {
                            float progress = page - lastPageIndex;
                            float alpha = (page - lastPageIndex) * 2 - 1;

                            float welcomeImageScale = ((progress) / (10f/3f) + 0.6f); //kinda offset
                            if (welcomeImageScale > 1) {
                                welcomeImageScale = 1;
                            }
                            lastPage.animate()
                                    .scaleX(welcomeImageScale).scaleY(welcomeImageScale)
                                    .y(lastPage.getHeight() * 0.6f * ((1 - progress))).alpha(progress).setDuration(0).start();

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



                            } else {
                                if (loginHolderStatus == LOGINHOLDER_STATUS_OUT) {
                                    loginHolderStatus = LOGINHOLDER_STATUS_VISIBLE;
                                    loginHolder.animate().y(0).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(200).setStartDelay(0).start();
                                }
                                if (!mainContentHidden) {
                                    // lastPage.animate().alpha(0).setDuration(0).start();
                                    backToTopArrow.animate().alpha(0).setDuration(0).start();
                                    backToTopText.animate().alpha(0).setDuration(0).start();
                                }
                            }
                        }
                    }
                }

                // paralax.scrollTo(0, (int) (paralax.getHeight()*(page/10)));
                int paralaxHolderHeight = paralax.getHeight();
                int paralaxImageHeight = paralaxImage.getHeight();
                float paralaxY = -(((float)  paralaxImageHeight - paralaxHolderHeight ) * ((float) page / (lastPageIndex)));
                paralaxImage.animate().y(paralaxY).setDuration(0).setStartDelay(0).start();

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
