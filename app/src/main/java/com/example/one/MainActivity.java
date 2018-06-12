package com.example.one;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.view.LayoutInflater;
import android.widget.TabHost;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
//    static {
//        System.loadLibrary("native-lib");
//    }

    public final int PAGE_ONE=0;
    public final int PAGE_TWO=1;
    public final int PAGE_THREE=2;
    FragmentTabHost mFragmentTabHost;
    public ViewPager viewPager;
    LayoutInflater mLayoutInflater;
//    List<TabItem> tabItemList= new ArrayList<>();
    List<ImageView> imageViewList=new ArrayList<>();
    List<Fragment> FragmentList=new ArrayList<>();
    SharedPreferences pre;
    int tabPager=0;


    static void toFragmentTwo(){

    }


    Class[] fragmentClass= {Fragment1.class, Fragment2.class,Fragment3.class};//先将Fragment定义为数组

    int[] image={R.drawable.home,R.drawable.setting,R.drawable.mine};
//    int[] imageChecked={R.drawable.home_checked,R.drawable.setting_checked,R.drawable.mine_checked};

    String[] imageText={"Home","Setting","Mine"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentTabHost=(FragmentTabHost) findViewById(R.id.tabHost);
        mLayoutInflater=LayoutInflater.from(this);
        viewPager=(ViewPager) findViewById(R.id.pagerView_fragment);


        pre= PreferenceManager.getDefaultSharedPreferences(this);//初始化SharedPreferences
        tabPager=pre.getInt("TabPager",0);

        initFragment();
        initFragmentTabHost();//完成FragmentTabHost的加载
        // Example of a call to a native method
//        TextView tv = (TextView) findViewById(R.id.sample_text);
//        tv.setText(stringFromJNI());

        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(),FragmentList));

        mFragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
//                for(int i=0;i<fragmentClass.length;i++){
//                    if (tabId.equals(tabItemList.get(i).tabTextView)){
//                        tabItemList.get(i).setTabImageView(true);
//                    }else{
//                        tabItemList.get(i).setTabImageView(false);
//                    }
//                }

                if(tabId.equals("Home")){
                    imageViewList.get(PAGE_ONE).setImageResource(R.drawable.home_checked);
                    imageViewList.get(PAGE_TWO).setImageResource(R.drawable.setting);
                    imageViewList.get(PAGE_THREE).setImageResource(R.drawable.mine);
                    tabPager=PAGE_ONE;
                    viewPager.setCurrentItem(PAGE_ONE);
                    Log.d("huan","TabHostHome");
                }else if (tabId.equals("Setting")){
                    imageViewList.get(PAGE_ONE).setImageResource(R.drawable.home);
                    imageViewList.get(PAGE_TWO).setImageResource(R.drawable.setting_checked);
                    imageViewList.get(PAGE_THREE).setImageResource(R.drawable.mine);
                    tabPager=PAGE_TWO;
                    viewPager.setCurrentItem(PAGE_TWO);
                    Log.d("huan","TabHostSetting");
                }else{
                    imageViewList.get(PAGE_ONE).setImageResource(R.drawable.home);
                    imageViewList.get(PAGE_TWO).setImageResource(R.drawable.setting);
                    imageViewList.get(PAGE_THREE).setImageResource(R.drawable.mine_checked);
                    tabPager=PAGE_THREE;
                    viewPager.setCurrentItem(PAGE_THREE);
                    Log.d("huan","TabHostMine");
                }

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mFragmentTabHost.setCurrentTab(position);
                tabPager=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        switch(tabPager){
            case PAGE_ONE:
//                mFragmentTabHost.setCurrentTab(tabPager);
                viewPager.setCurrentItem(tabPager);
                Log.d("huan","Switch tabPager PAGE_ONE");
                imageViewList.get(PAGE_ONE).setImageResource(R.drawable.home_checked);
                break;
            case PAGE_TWO:
//                mFragmentTabHost.setCurrentTab(tabPager);
                viewPager.setCurrentItem(tabPager);
                break;
            case PAGE_THREE:
//                mFragmentTabHost.setCurrentTab(tabPager);
                viewPager.setCurrentItem(tabPager);
                break;
        }
    }

    @Override
    public void onStart(){
        super.onStart();

    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
//    public native String stringFromJNI();

    private void initFragmentTabHost(){
        //步骤1:给定Fragment的容器
        mFragmentTabHost.setup(this,getSupportFragmentManager(),R.id.pagerView_fragment);
        //步骤2:开启for循环把对应数量的Tab设置上去
//        tabItemList.clear();
        imageViewList.clear();
        for (int i=0;i<fragmentClass.length;i++){
            //每循环一次设置一个图标和文字
            TabHost.TabSpec tabSpec=mFragmentTabHost.newTabSpec(imageText[i]).setIndicator(initItemView(i));
            mFragmentTabHost.addTab(tabSpec,fragmentClass[i],null);//将tabSpec添加到Tab,且将其和对应的Fragment绑定
            mFragmentTabHost.getTabWidget().setBackgroundResource(R.color.write);
        }
    }

    private View initItemView(int index){
        View view =mLayoutInflater.inflate(R.layout.tab_item_view,null);

        ImageView imageView01=(ImageView)view.findViewById(R.id.imageView01);
        TextView textView01=(TextView)view.findViewById(R.id.textView01);
//        tabItemList.add(new TabItem(image[index],imageChecked[index],imageView01,imageText[index]));
//        temp[index]=imageView01;
        imageViewList.add(imageView01);//把每次的ImageView都存放到List里面，这样可以实现对已经循环过去了的ImageView的操作。
        imageView01.setImageResource(image[index]);
        textView01.setText(imageText[index]);

        return view;
    }

    private void initFragment(){
        FragmentList.add(new Fragment1());
        FragmentList.add(new Fragment2());
        FragmentList.add(new Fragment3());
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        SharedPreferences.Editor editor=pre.edit();
        editor.putInt("TabPager",tabPager);
        editor.apply();
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState){
        super.onSaveInstanceState(saveInstanceState);
    }

}
