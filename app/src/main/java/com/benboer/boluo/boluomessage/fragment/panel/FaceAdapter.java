package com.benboer.boluo.boluomessage.fragment.panel;

import android.view.View;

import com.benboer.boluo.boluomessage.R;
import com.benboer.boluo.widget.recycler.RecyclerAdapter;
import com.benboer.boluo.face.Face;

import java.util.List;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/24.
 */
public class FaceAdapter extends RecyclerAdapter<Face.Bean> {
    public FaceAdapter(List<Face.Bean> beans, AdapterListener<Face.Bean> listener) {
        super(beans, listener);
    }

    @Override
    protected int getItemViewType(int position, Face.Bean bean) {
        return R.layout.cell_face;
    }

    @Override
    protected ViewHolder<Face.Bean> onCreateViewHolder(View root, int viewType) {
        return new FaceHolder(root);
    }
}
