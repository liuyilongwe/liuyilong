package horizontalscrollview01.com.horizontalscrollview01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private ImageSwitcher switcher;
    private List<Integer> imgIds;
    private ImageView[] imageViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout = (LinearLayout) findViewById(R.id.linear_layout);
        switcher = (ImageSwitcher) findViewById(R.id.sc);
        switcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView img = new ImageView(MainActivity.this);
                return img;
            }
        });
        imgIds = getImageIds();
        switcher.setImageResource(imgIds.get(0));
        init();
    }

    private void init() {
        imageViews = new ImageView[imgIds.size()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(60, 80);
        layoutParams.setMargins(0,0,5,0);
        for (int i = 0;i < imageViews.length; i++){
            imageViews[i] = new ImageView(this);
            imageViews[i].setId(imgIds.get(i));
            imageViews[i].setBackgroundResource(R.drawable.bg);
            imageViews[i].setImageResource(imgIds.get(i));
            imageViews[i].setLayoutParams(layoutParams);
            imageViews[i].setOnClickListener(new MyClickListener());

            if (i != 0){
                imageViews[i].setImageAlpha(100);

            }else {
                imageViews[i].setImageAlpha(255);

            }
            linearLayout.addView(imageViews[i]);
        }
    }

    public List<Integer> getImageIds() {
        List<Integer> imageIds = new ArrayList<Integer>();
        try {
            Field[] drawableFields = R.drawable.class.getFields();
            for (Field field:drawableFields){
                if (field.getName().startsWith("x_")){
                    imageIds.add(field.getInt(R.drawable.class));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageIds;
    }

    public void setAlpha(ImageView[] imageViews) {
        for (int i = 0;i < imageViews.length;i++){
            imageViews[i].setImageAlpha(100);
        }
    }

    private class MyClickListener implements View.OnClickListener {
        @SuppressWarnings("ResourceType")
        @Override
        public void onClick(View view) {
            switcher.setImageResource(view.getId());
                 setAlpha(imageViews);
                ((ImageView)view).setImageAlpha(255);

        }
    }


}
