package com.wind.administrator.fuck.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.wind.administrator.fuck.R;
import com.wind.administrator.fuck.bean.RShopCar;
import com.wind.administrator.fuck.cons.NetworkConstant;
import com.wind.administrator.fuck.fragment.ShopcarFragment;
import com.wind.administrator.fuck.listener.IShopCarDeleteListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/20 0020.
 * dataBinding应用
 */

public class ShopCarAdapter extends JDBaseAdapter<RShopCar> {
    //用来记录当前item是否选中的队列
    private List<Boolean> itemChecked = new ArrayList<>();
    private IShopCarDeleteListener mListener;

    /**
     * 用来设置摸个item是否选中
     *
     * @param poision
     */
    public void setItemChecked(int poision) {
        itemChecked.set(poision, !(itemChecked.get(poision)));
        notifyDataSetChanged();
    }

    /**
     * 全选按钮监听事件
     */
    public void setAllCheck() {
        //如果队列里面有一个没选中，则设置全部选中， 如果全选了 就设置全部不选中
        /*if(itemChecked.contains(false)){//包含 false
            for (int i = 0; i < itemChecked.size(); i++) {
                itemChecked.set(i,true);
            }
        }else {//没有false （全部选中状态）
            for (int i = 0; i < itemChecked.size(); i++) {
                itemChecked.set(i,false);
            }
        }*/
        boolean ifHashItemChecked = itemChecked.contains(false);//在for循环中boolean会一直改变，所以要抽出来
        for (int i = 0; i < itemChecked.size(); i++) {
            itemChecked.set(i, ifHashItemChecked);
        }

        notifyDataSetChanged();

    }

    public boolean ifAllChecked(){
        return !itemChecked.contains(false);//如果包含false--》至少有一个没选中,取反就全部设置为true
    }

    /**
     * @return 获取选中商品的个数
     */
    public int getCheckProductCount(){
        int result = 0 ;
        for (int i = 0; i < itemChecked.size(); i++) {
            if(itemChecked.get(i)){
                  result++;
            }
        }
        notifyDataSetChanged();
        return result ;
    }
    /**
     * @return 获取选中商品的总金额
     */
    public double getCheckProductPriceCount(){
        double result = 0 ;
        for (int i = 0; i < itemChecked.size(); i++) {
            if(itemChecked.get(i)){//选中
                RShopCar bean = mDatas.get(i);
                result += bean.getPprice() * bean.getBuyCount() ;//单价乘数量
            }
        }
        notifyDataSetChanged();
        return result ;
    }
    @Override
    public void setDatas(List<RShopCar> datas) {
        super.setDatas(datas);
        //数据有多少个，记录就有多少个 默认情况都是未选的
        for (int i = 0; i < datas.size(); i++) {
            itemChecked.add(false);
        }
    }

    /**
     *
     * @return 返回选中商品的结果集
     */
    public List<RShopCar> getCheckedItems(){
        //创建一个结果集
        ArrayList<RShopCar> checkItems = new ArrayList<>();
        //遍历购物车列表
        for (int i = 0; i < itemChecked.size(); i++) {
            //判断是否被选中
            if(itemChecked.get(i)){
                //将选中的商品的所有信息添加到结果集中
                checkItems.add(mDatas.get(i));
            }
        }
        return checkItems ;
    }
    /**
     * 构造器
     *
     * @param context
     */
    public ShopCarAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.shopcar_lv_item_layout, parent, false);
            holder = new ViewHolder();
            holder.itemCbx = (CheckBox) convertView.findViewById(R.id.cbx);
            holder.iconIv = (ImageView) convertView.findViewById(R.id.product_iv);
            holder.pnameTv = (TextView) convertView.findViewById(R.id.name_tv);
            holder.pversionTv = (TextView) convertView.findViewById(R.id.version_tv);
            holder.ppriceTv = (TextView) convertView.findViewById(R.id.price_tv);
            holder.buyCountTv = (TextView) convertView.findViewById(R.id.buyCount_tv);
            holder.deletBtn = (TextView) convertView.findViewById(R.id.delete_product);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final RShopCar bean = mDatas.get(position);
        mAsyncImageLoader.displayImage(NetworkConstant.BASE_URL + bean.getPimageUrl(), holder.iconIv);

        holder.pnameTv.setText(bean.getPname());
        holder.pversionTv.setText(bean.getPversion());
        holder.pversionTv.setText("¥ " + bean.getPprice());
        holder.buyCountTv.setText("x " + bean.getBuyCount());
        holder.itemCbx.setChecked(itemChecked.get(position));
        holder.deletBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击 发送一个网络请求
                if(mListener != null){
                    //删除
                    mListener.onItemDeleted(bean.getId());
                }

            }
        });
        return convertView;
    }

    public void setListener(IShopCarDeleteListener listener) {
        mListener = listener;
    }

    class ViewHolder {
        CheckBox itemCbx;
        ImageView iconIv;
        TextView pnameTv;
        TextView pversionTv;
        TextView ppriceTv;
        TextView buyCountTv;
        TextView deletBtn;
    }

    @Override
    public long getItemId(int position) {
        return mDatas != null ? mDatas.get(position).getId() : 0;
    }


}















