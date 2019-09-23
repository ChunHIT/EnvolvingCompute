package GA;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Data {
	public static double[][] distance;
	public static int citycount;
	public Data(String filename) throws IOException{
		filename = filename + ".tsp";
		// 读取数据tsp里的数据包括第I个城市与城市的X,Y坐标
		int[] x;
		int[] y;
		String strbuff;
		BufferedReader tspdata = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
		strbuff = tspdata.readLine();
		Pattern pattern = Pattern.compile("DIMENSION: (\\d+)");
		Matcher matcher = pattern.matcher(strbuff);
		while(strbuff!=null && !matcher.find()){
			strbuff = tspdata.readLine();
			matcher = pattern.matcher(strbuff);
		}
		citycount=Integer.valueOf(matcher.group(1));
		pattern = Pattern.compile("NODE_COORD_SECTION");
		matcher = pattern.matcher(strbuff);
		while(strbuff!=null && !matcher.find()){
			strbuff = tspdata.readLine();
			matcher = pattern.matcher(strbuff);
		}
		distance = new double[citycount][citycount];
		x = new int[citycount];
		y = new int[citycount];
		for (int citys = 0; citys < citycount; citys++) {
			strbuff = tspdata.readLine().replaceAll("[ ]+", " ");
			//System.out.println(strbuff);
			String[] strcol = strbuff.split(" ");
			x[citys] = Integer.valueOf(strcol[1]);
			y[citys] = Integer.valueOf(strcol[2]);
		}
		// 计算两个城市之间的距离矩阵，并更新距离矩阵
		for (int city1 = 0; city1 < citycount - 1; city1++) {
			distance[city1][city1] = 0;
			for (int city2 = city1 + 1; city2 < citycount; city2++) {
				distance[city1][city2] = (int) (Math.sqrt(
						(x[city1] - x[city2]) * (x[city1] - x[city2]) + (y[city1] - y[city2]) * (y[city1] - y[city2])));
				distance[city2][city1] = distance[city1][city2];// 距离矩阵是对称矩阵
			}
		}
	}

    /*public static double[][] XY(){
        double [][] xy = new double [][] {
            {    1304    ,    2312    }    ,
            {    3639    ,    1315    }    ,
            {    4177    ,    2244    }    ,
            {    3712    ,    1399    }    ,
            {    3488    ,    1535    }    ,
            {    3326    ,    1556    }    ,
            {    3238    ,    1229    }    ,
            {    4196    ,    1004    }    ,
            {    4312    ,    790    }    ,
            {    4386    ,    570    }    ,
            {    3007    ,    1970    }    ,
            {    2562    ,    1756    }    ,
            {    2788    ,    1491    }    ,
            {    2381    ,    1676    }    ,
            {    1332    ,    695    }    ,
            {    3715    ,    1678    }    ,
            {    3918    ,    2179    }    ,
            {    4061    ,    2370    }    ,
            {    3780    ,    2212    }    ,
            {    3676    ,    2578    }    ,
            {    4029    ,    2838    }    ,
            {    4263    ,    2931    }    ,
            {    3429    ,    1908    }    ,
            {    3507    ,    2367    }    ,
            {    3394    ,    2643    }    ,
            {    3439    ,    3201    }    ,
            {    2935    ,    3240    }    ,
            {    3140    ,    3550    }    ,
            {    2545    ,    2357    }    ,
            {    2778    ,    2826    }    ,
            {    2370    ,    2975    }    
        };
        return xy;
    }*/
}