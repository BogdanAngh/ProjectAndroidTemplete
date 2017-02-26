package com.example.duy.calculator.view;

import android.content.ClipData;
import android.content.ClipData.Item;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.duy.calculator.AbstractSolve;
import com.example.duy.calculator.R;
import com.example.duy.calculator.utils.VariableUtil;
import java.util.ArrayList;

public class FunctionRecyclerView extends RecyclerView {

    static class FunctionAdapter extends Adapter<ViewHolder> {
        private AbstractSolve context;
        private ArrayList mList;

        class 1 implements OnClickListener {
            1() {
            }

            public void onClick(View view) {
                FunctionAdapter.this.context.insertTextDisplay(((TextView) view).getText().toString().trim());
            }
        }

        class 2 implements OnLongClickListener {
            2() {
            }

            public boolean onLongClick(View view) {
                String[] mimeTypes = new String[]{"text/plain"};
                view.startDrag(new ClipData(((TextView) view).getText().toString(), mimeTypes, new Item(((TextView) view).getText().toString())), new DragShadowBuilder(view), view, 0);
                return false;
            }
        }

        static class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
            TextView txtContent;

            ViewHolder(View itemView) {
                super(itemView);
                this.txtContent = (TextView) itemView.findViewById(R.id.text_view);
            }
        }

        private FunctionAdapter(AbstractSolve context) {
            this.mList = new ArrayList();
            this.mList = VariableUtil.getListFunction(context);
            this.context = context;
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.function_text, parent, false));
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.txtContent.setText(this.mList.get(position).toString());
            holder.txtContent.setOnClickListener(null);
            holder.txtContent.setOnClickListener(new 1());
            holder.txtContent.setOnLongClickListener(new 2());
        }

        public int getItemCount() {
            return this.mList.size();
        }
    }

    public FunctionRecyclerView(Context context) {
        super(context);
        if (!isInEditMode()) {
            init((AbstractSolve) context);
        }
    }

    public FunctionRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init((AbstractSolve) context);
        }
    }

    public FunctionRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            init((AbstractSolve) context);
        }
    }

    private void init(AbstractSolve context) {
        FunctionAdapter mAdapter = new FunctionAdapter(null);
        setLayoutManager(new LinearLayoutManager(context, 0, false));
        setAdapter(mAdapter);
    }
}
