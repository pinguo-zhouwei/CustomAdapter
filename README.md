### RecyclerView Adapter 优雅封装，一个Adapter搞定所有列表。帮你快速构建一个列表

  
### 目的
1, 构造一个通用的Adapter模版，避免每添加一个列表就要写一个Adapter,避免写Adapter中的大量重复代码。
2，通过组装的方式来构建Adapter,将每一种(ViewType不同的)Item抽象成一个单独组件，Adapter 就是一个壳，我们只需要向Adapter中添加Item就行，这样做的好处就是减少耦合，去掉一种item 或者添加一种item对于列表是没有任何影响的。
3,高内聚，低耦合，扩展方便。


### 添加依赖
1 Add it in your root build.gradle at the end of repositories:
```java
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2. Add the dependency
```java
dependencies {
	 compile 'com.github.pinguo-zhouwei:CustomAdapter:v1.0.1'
}
```


### 思路

为每一种 viewType 定义一个Cell,Cell就是上面提到的独立组件，它负责创建ViewHolder,数据绑定和逻辑处理。
它有2个重要的方法，onCreateViewHolder 负责创建ViewHolder,onBindViewHolder负责数据绑定，这两个方法的定义和生命周期同Adapter种的2个方法一样，事实上,Adapter 中的onCreateViewHolder和onBindViewHolder 最终调用的是Cell中的方法。

### 特性

1,快速构建一个列表（多item或者单item）
2,带有下拉刷新(Google 的SwipeRefreshLayout)
3，上拉加载更多（可自定义）
4，显示列表Loading态（可自定义）
5，显示列表出错状态（可自定义）
6，显示列表空状态（可自定义）

#### 如何快速构建一个列表？

##### 添加一个多Item的列表:
**1，创建一个Fragment继承AbsBaseFragment，实现几个方法。**
```java
/**
 * Created by zhouwei on 17/2/3.
 */

public class HomePageFragment extends AbsBaseFragment<Entry> {
    @Override
    public void onRecyclerViewInitialized() {
       //初始化View和数据加载 
    }

    @Override
    public void onPullRefresh() {
        //下拉刷新回调
       
    }

    @Override
    public void onLoadMore() {
        //上拉加载回调
    }
    protected List<Cell> getCells(List<Entry> entries){
        //根据实体生成Cell
        return null;
    }

}
```
实现上面几个抽象方法，实际上只实现onRecyclerViewInitialized和getCells两个方法就可以实现列表，其它两个方法是下拉刷新和上拉加载的。

**2,创建Cell类**
```java
/**
 * Created by zhouwei on 17/2/7.
 */

public class BannerCell extends RVBaseCell<List<String>> {
    public static final int TYPE = 2;
    private ConvenientBanner mConvenientBanner;
    public BannerCell(List<String> strings) {
        super(strings);
    }

    @Override
    public int getItemType() {
        return TYPE;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVBaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_cell_layoout,null));
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {
       mConvenientBanner = (ConvenientBanner) holder.getView(R.id.banner);
        mConvenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, mData);
        mConvenientBanner.startTurning(2000);
    }

    @Override
    public void releaseResource() {
        if(mConvenientBanner!=null){
            mConvenientBanner.stopTurning();
        }
    }

    public static class NetworkImageHolderView implements CBPageAdapter.Holder<String>{
        private ImageView imageView;
        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            ImageLoader.getInstance().displayImage(data,imageView);
        }
    }
}
```
```java
/**
 * Created by zhouwei on 17/1/19.
 */

public class ImageCell extends RVBaseCell<Entry> {
    public static final int TYPE = 1;
    public ImageCell(Entry entry) {
        super(entry);
    }

    @Override
    public int getItemType() {
        return TYPE;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVBaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.image_cell_layout,null));
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {
        Picasso.with(holder.getItemView().getContext()).load(mData.imageUrl).into(holder.getImageView(R.id.image));
    }

}
```
```java
/**
 * Created by zhouwei on 17/1/19.
 */

public class TextCell extends RVBaseCell<Entry> {
    public static final int TYPE = 0;
    public TextCell(Entry entry) {
        super(entry);
    }

    @Override
    public int getItemType() {
        return TYPE;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVBaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.text_cell_layout,null));
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {
       holder.setText(R.id.text_content,mData.content);
    }
}
```
上面创建了3个Cell,也就是这个列表包含了3种不同类型的Item。
> 注意：一个列表内，每个Cell 的TYPE要不相同，也就是getItemType方法的返回值要不同。

**3，onRecyclerViewInitialized ，做初始化和加载数据**
```java
@Override
    public void onRecyclerViewInitialized() {
       //初始化View和数据加载
       //设置刷新进度条颜色
       setColorSchemeResources(R.color.colorAccent);
       loadData();
    }

    /**
     * 模拟从服务器取数据
     */
    private void loadData(){
        View loadingView = LayoutInflater.from(getContext()).inflate(R.layout.manu_loading_layout,null);
        mBaseAdapter.showLoading(loadingView);
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBaseAdapter.hideLoading();
                mBaseAdapter.addAll(getCells(mockData()));
            }
        },2000);
    }
```
4,实现getCells方法，生成Cell
```java

   protected List<Cell> getCells(List<Entry> entries){
        //根据实体生成Cell
        List<Cell> cells = new ArrayList<>();
        cells.add(new BannerCell(Arrays.asList(DataMocker.images)));
        for (int i=0;i<entries.size();i++){
            Entry entry = entries.get(i);
            if(entry.type == Entry.TYPE_IMAGE){
                cells.add(new ImageCell(entry));
            }else{
                cells.add(new TextCell(entry));
            }
        }
        return cells;
    }
```
上面根据实体生成不同的Cell。有三种Cell,BannerCell,ImageCell和TextCell。

**以上4个步骤就能实现一个界面复杂包含多做Item的列表了**效果图如下：


![adapter_cell](image/adapter_cell.gif)

##### Grid 列表和瀑布流列表：
上面演示了添加多Item type 的列表，添加单Item的列表也是一样的，只不过只有一个Cell而已。添加Grid 列表和瀑布流列表差不多的，只是RecylerView 的LayoutManager不同而已。

瀑布流列表示例：
```java
/**
 * Created by zhouwei on 17/2/4.
 */

public class DetailFragment extends AbsBaseFragment<DetailEntry> {
    @Override
    public void onRecyclerViewInitialized() {
         mBaseAdapter.setData(getCells(mockStaggerData()));
    }

    @Override
    public void onPullRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    protected RecyclerView.LayoutManager initLayoutManger() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        return layoutManager;
    }

    @Override
    protected List<Cell> getCells(List<DetailEntry> list) {
        List<Cell> cells = new ArrayList<>();
        for (int i=0;i<list.size();i++){
            cells.add(new DetailCell(list.get(i)));
        }
        return cells;
    }
    
}
```
只需要重写initLayoutManager这个方法，返回一个瀑布流的LayoutMannger就可以了。
效果如下：

![stagger_adapter_cell.gif](http://upload-images.jianshu.io/upload_images/3513995-b0a50896ce440b8a.gif?imageMogr2/auto-orient/strip)


### 其它演示示例：加载更多、Loading View 、Error View ，Empty View 

**1,显示LoadMoreView**
提供了默认的LoadingView，调用代码如下：
```java
 mBaseAdapter.showLoadMore();
```
隐藏LoadMore View 调用如下代码：

```java
 if(mBaseAdapter!=null){
            mBaseAdapter.hideLoadMore();
   }
```

效果图看上面演示的瀑布流效果图。

**2,显示loading View **
提供了默认的Loading View，调用代码如下：
 ```java
 mBaseAdapter.showLoading();
```
当然也可以自定义Loading View，提供一个布局即可：
```java
View loadingView = LayoutInflater.from(getContext()).inflate(R.layout.manu_loading_layout,null);
 mBaseAdapter.showLoading(loadingView);
```
效果如下：

![loading_view.png](http://upload-images.jianshu.io/upload_images/3513995-b7f9fe16491d21d3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

还有一种情况是，顶部有一个固定的HeaderCell，不需要加载数据，显示静态页面，下面加载数据时需要Loading态，Error状态，Empty状态等等。提供如下3个方法：

* showLoadingKeepCount(int keepCount,int height,View loadingView)
列表Loading状态显示的View，保留keepCountg个Item，并指定高度，指定显示的View

* showLoadingKeepCount(int keepCount,int height)
列表Loading状态显示的View，保留keepCountg个Item，并指定高度（显示的是提供的默认Loading View）

* showLoadingKeepCount(int keepCount)
显示默认LoadingView

使用代码如下：
```java
 View loadingView = LayoutInflater.from(getContext()).inflate(R.layout.manu_loading_layout,null);
mBaseAdapter.showLoadingKeepCount(1,height,loadingView);
```
效果图如下：

![loading_view_keep_count.png](http://upload-images.jianshu.io/upload_images/3513995-43a493420fe464c4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

隐藏Loading View 调用对应hide 方法：
```java
 mBaseAdapter.hideLoading();
```

** 3, Error View  和 Empty View **
显示Error View 和Empty View 与Loading View 的显示与隐藏是一样，不在过多讲，直接去看源码，提供了几个方法：

![error_method.png](http://upload-images.jianshu.io/upload_images/3513995-08ab75a87f417cca.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
效果图：



![error_tip.png](http://upload-images.jianshu.io/upload_images/3513995-212a09bb7e71a5be.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

Empty View 的显示完全一样,详情请看源码。