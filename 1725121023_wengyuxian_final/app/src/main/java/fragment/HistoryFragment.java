package fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a1725121023_wengyuxian_final.MainActivity;
import com.example.a1725121023_wengyuxian_final.R;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class HistoryFragment extends Fragment {
    public TextView textView;
    protected boolean isCreated = false;
    public String FILE_PATH;

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        System.out.println("onCreateView生命周期测试");
        View view = inflater.inflate(R.layout.view_history, null);
        textView =  view.findViewById(R.id.history);
        String history = bufferRead(FILE_PATH);
        textView.setText(history);
        isCreated = true;
        return view;
    }

    // 重写方法使数据更新可见，该方法调用早于onCreate()
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        System.out.println("setUserVisibleHint生命周期测试");
        /**
         * 最初的设计是:如果onCreateView或者onAttach未执行,FILE_PATH值为null,直接返回
         *
         * 我认为:这会导致第一次点击History无效(实际上并不)
         */
        if (FILE_PATH == null) {
            return;
        }
        // 读取历史记录文件
        if (isVisibleToUser) {
            String historyResult = bufferRead(FILE_PATH);
            textView.setText(historyResult);
        }
    }

    // 获取常量
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        System.out.println("onAttach生命周期测试");
        //通过强转成宿主activity，就可以获取到传递过来的数据
        FILE_PATH = ((MainActivity) activity).getPath();
        System.out.println("onAttach传参测试 " + FILE_PATH);
    }

    // 获取最新10条历史纪录
    public static String bufferRead(String filePath) {
        try {
            ArrayList<String> historyList = new ArrayList<>();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            String line = bufferedReader.readLine();
            StringBuilder builder = new StringBuilder();
            while (line != null) {
                historyList.add(line);
                line = bufferedReader.readLine();
            }
            Collections.reverse(historyList);

            for (int i = 0; i < 10 && i < historyList.size(); i++) {
                builder.append(historyList.get(i));
                builder.append("\n");
            }
            bufferedReader.close();
            Log.d("wyx", "读取结果: " + builder.toString());
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
