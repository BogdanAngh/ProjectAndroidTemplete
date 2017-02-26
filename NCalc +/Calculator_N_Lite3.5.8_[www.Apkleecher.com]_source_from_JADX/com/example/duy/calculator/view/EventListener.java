package com.example.duy.calculator.view;

import android.animation.Animator;
import android.content.ClipData;
import android.content.ClipData.Item;
import android.content.Intent;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnKeyListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import com.example.duy.calculator.AbstractCalculator;
import com.example.duy.calculator.AbstractNavDrawer;
import com.example.duy.calculator.MainCalculatorActivity;
import com.example.duy.calculator.R;
import com.example.duy.calculator.SettingsActivity;
import io.codetail.animation.ViewAnimationUtils;

public class EventListener implements OnKeyListener, OnLongClickListener, OnDragListener, OnClickListener, OnTouchListener {
    AbstractNavDrawer mHandler;

    public void setHandler(AbstractCalculator calculator) {
        this.mHandler = calculator;
    }

    public void setHandler(AbstractNavDrawer equation) {
        this.mHandler = equation;
    }

    private void animateView(View view) {
        int cx = (view.getLeft() + view.getRight()) / 2;
        int cy = (view.getTop() + view.getBottom()) / 2;
        Animator animator = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0.0f, (float) Math.hypot((double) Math.max(cx, view.getWidth() - cx), (double) Math.max(cy, view.getHeight() - cy)));
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration((long) view.getContext().getResources().getInteger(17694722));
        animator.start();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_solve /*2131689652*/:
                ((MainCalculatorActivity) this.mHandler).solve();
            case R.id.img_setting /*2131689848*/:
                this.mHandler.startActivity(new Intent(this.mHandler, SettingsActivity.class));
            case R.id.img_share /*2131689849*/:
                ((MainCalculatorActivity) this.mHandler).shareText();
            case R.id.img_copy /*2131689850*/:
                ((MainCalculatorActivity) this.mHandler).copyText();
            case R.id.img_paste /*2131689851*/:
                ((MainCalculatorActivity) this.mHandler).pasteText();
            case R.id.img_help /*2131689852*/:
                ((MainCalculatorActivity) this.mHandler).onHelpFunction();
            case R.id.btn_asin /*2131689853*/:
            case R.id.btn_arccos /*2131689854*/:
            case R.id.btn_arctan /*2131689855*/:
            case R.id.btn_log /*2131689856*/:
            case R.id.btnSinh /*2131689857*/:
            case R.id.btnCosh /*2131689858*/:
            case R.id.btnTanh /*2131689859*/:
            case R.id.btn_exp /*2131689860*/:
            case R.id.btn_arcsinh /*2131689861*/:
            case R.id.btn_arccosh /*2131689862*/:
            case R.id.btn_arctanh /*2131689863*/:
            case R.id.btn_mod /*2131689864*/:
            case R.id.btn_ln /*2131689865*/:
            case R.id.btn_abs /*2131689866*/:
            case R.id.btn_percent /*2131689868*/:
            case R.id.btn_sign /*2131689869*/:
            case R.id.btn_gcd /*2131689871*/:
            case R.id.btn_lcm /*2131689872*/:
            case R.id.btn_combi /*2131689873*/:
            case R.id.btn_perm /*2131689874*/:
            case R.id.btn_ceil /*2131689875*/:
            case R.id.btn_floor /*2131689876*/:
            case R.id.btn_min /*2131689877*/:
            case R.id.btn_max /*2131689878*/:
            case R.id.btn_sin /*2131689930*/:
            case R.id.btn_cos /*2131689931*/:
            case R.id.btn_tan /*2131689932*/:
            case R.id.btn_sqrt /*2131689937*/:
            case R.id.btn_cbrt /*2131689938*/:
                ((AbstractCalculator) this.mHandler).insertText(((Button) view).getText().toString() + this.mHandler.getResources().getString(R.string.leftParen));
            case R.id.btnDelete /*2131689892*/:
                ((AbstractCalculator) this.mHandler).onDelete();
            case R.id.btnPlus /*2131689893*/:
            case R.id.btnMinus /*2131689894*/:
            case R.id.btnMul /*2131689895*/:
            case R.id.btnDiv /*2131689919*/:
                ((AbstractCalculator) this.mHandler).insertOperator(" " + ((Button) view).getText().toString() + " ");
            case R.id.btnEqual /*2131689902*/:
                ((AbstractCalculator) this.mHandler).onEqual();
            case R.id.btnClear /*2131689920*/:
                ((AbstractCalculator) this.mHandler).onClear();
            case R.id.btn_calc /*2131689924*/:
                ((MainCalculatorActivity) this.mHandler).onDefineAndCalc();
            case R.id.btn_fact /*2131689925*/:
                ((MainCalculatorActivity) this.mHandler).factorInteger();
            case R.id.btn_input_voice /*2131689928*/:
                ((AbstractCalculator) this.mHandler).onInputVoice();
            case R.id.btnFact /*2131689935*/:
            case R.id.btnPower /*2131689936*/:
                ((AbstractCalculator) this.mHandler).insertOperator(((Button) view).getText().toString());
            default:
                if (view instanceof Button) {
                    ((AbstractCalculator) this.mHandler).insertText(((Button) view).getText().toString());
                }
        }
    }

    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        return false;
    }

    @Deprecated
    public boolean onDrag(View view, DragEvent dragEvent) {
        return false;
    }

    public boolean onTouch(View v, MotionEvent motionEvent) {
        if (motionEvent.getAction() != 0) {
            return false;
        }
        String[] mimeTypes = new String[]{"text/plain"};
        v.startDrag(new ClipData(((TextView) v).getText().toString(), mimeTypes, new Item(((TextView) v).getText().toString())), new DragShadowBuilder(v), v, 0);
        return true;
    }

    public boolean onLongClick(View v) {
        if (!(v instanceof ImagePadButton)) {
            int id = v.getId();
            if (!(id == R.id.btn_solve || id == R.id.btnEqual || id == R.id.btnDelete || id == R.id.btnClear)) {
                String[] mimeTypes = new String[]{"text/plain"};
                v.startDrag(new ClipData(((TextView) v).getText().toString(), mimeTypes, new Item(((TextView) v).getText().toString())), new DragShadowBuilder(v), v, 0);
            }
        }
        return false;
    }
}
