package cn.btzh.wlsccalculator.ui.recyler.interfaces;


import cn.btzh.wlsccalculator.ui.recyler.ViewHolder;

/**
 * Author: Othershe
 * Time: 2016/8/29 10:48
 */
public interface OnSwipeMenuClickListener<T> {
    void onSwipMenuClick(ViewHolder viewHolder, T data, int position);
}
