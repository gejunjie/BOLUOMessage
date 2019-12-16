package com.benboer.boluo.message.fragment.panel;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.benboer.boluo.common.comm_ui.recycler.RecyclerAdapter;
import com.benboer.boluo.message.R;
import com.benboer.boluo.message.R2;
import com.benboer.boluo.message.widget.face.Face;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;

import butterknife.BindView;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/24.
 */
public class FaceHolder extends RecyclerAdapter.ViewHolder<Face.Bean> {

    @BindView(R2.id.im_face)
    ImageView mFace;

    public FaceHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    protected void onBind(Face.Bean bean) {
        if (bean != null
                // drawable 资源 id
                && ((bean.preview instanceof Integer)
                // face zip 包资源路径
                || bean.preview instanceof String))
            Glide.with(itemView.getContext())
                    .load(bean.preview)
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .placeholder(R.drawable.default_face)
                    .into(mFace);
    }
}
