package com.suwish.proc.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;


/**
 *
 * 对话框创建的快捷帮助类，主要创建基于6.0并向下兼容的对话框。
 * <p/>
 * 由于使用appcompat 所以这里使用v7版本的AlertDialog便于进行
 * 主题修改，系统原生AlertDialog存在一定的兼容问题，麻烦的一逼
 *
 * <p/>
 *
 * TODO single dialog for dismiss last dialog <br/>
 * TODO use {@link DialogInterface} instead of BUTTON option
 *
 * @author min.su on 2016/12/28.
 */
public final class DialogHelper{

    public static final int BUTTON_CONFIRM = 1;
    public static final int BUTTON_CANCEL = 2;
    public static final int BUTTON_DISMISS = 3;

    private Context context;

    private DialogHelper(Context context){
        this.context = context;
    }

    public static DialogHelper create(Context context){
        return new DialogHelper(context);
    }

    public interface DialogCallback{
        void onDialogClick(DialogInterface dialogInterface, int button);
    }

    public interface DialogInputCallBack{
        void onDialogInput(DialogInterface dialogInterface, String text);
    }

    public static ProgressDialog createDialog(Context context){
        ProgressDialog dialog = new ProgressDialog(context);
//        dialog.getWindow().setBackgroundDrawableResource(R.drawable.background_border);
        return dialog;
    }

    public AlertDialog create(int title, int message, DialogCallback callback){
        return create(context.getString(title), context.getString(message), callback);
    }

    public AlertDialog create(String title, String message, final DialogCallback callback){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (callback == null) return;
                switch (i){
                    case DialogInterface.BUTTON_POSITIVE:
                        callback.onDialogClick(dialogInterface, BUTTON_CONFIRM);
                        break;
                    case  DialogInterface.BUTTON_NEGATIVE:
                        callback.onDialogClick(dialogInterface, BUTTON_CANCEL);
                        break;
                }
            }
        };
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (callback == null) return;
                callback.onDialogClick(dialogInterface, BUTTON_DISMISS);
            }
        });
        builder.setPositiveButton(android.R.string.ok, clickListener);
        builder.setNegativeButton(android.R.string.cancel, clickListener);
        return builder.show();
    }

    /**
     * 实际上是一个消息对话框，设计目的是带标题的提示信息。
     * 而回调方法仅仅监听Dismiss事件，不监听按钮点击。
     * 而create(XX)方法同时监控<code>OK</code>、<code>CANCEL</code>
     * 和<code>DISMISS</code>事件，最重要的是其还包括一个取消按钮
     *
     * @param title title
     * @param message message
     * @param callback callback
     * @return AlertDialog
     */
    public AlertDialog show(String title, String message, final DialogCallback callback){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (callback == null) return;
                callback.onDialogClick(dialogInterface, BUTTON_DISMISS);
            }
        });
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (callback == null) return;
                callback.onDialogClick(dialog, BUTTON_CONFIRM);
            }
        });
        return builder.show();
    }

    public AlertDialog show(int title, int message, final DialogCallback callback){
        return show(context.getString(title), context.getString(message), callback);
    }

    public AlertDialog createChoiceDialog(int title, ListAdapter adapter, int position,
                                          DialogInterface.OnClickListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setSingleChoiceItems(adapter, position, listener);
        builder.setPositiveButton(android.R.string.cancel, null);
        return builder.show();
    }

    public AlertDialog createCustomDialog(int title, ListAdapter adapter,
                                         DialogInterface.OnClickListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setSingleChoiceItems(adapter, 0, listener);
        builder.setPositiveButton(android.R.string.cancel, null);
        return builder.show();
    }

    public AlertDialog createCustomDialog(int title, View contentView, final DialogCallback callback){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setView(contentView);
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (callback == null) return;
                callback.onDialogClick(dialogInterface, BUTTON_DISMISS);
            }
        });
        DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (callback == null) return;
                switch (i){
                    case DialogInterface.BUTTON_POSITIVE:
                        callback.onDialogClick(dialogInterface, BUTTON_CONFIRM);
                        break;
                    case  DialogInterface.BUTTON_NEGATIVE:
                        callback.onDialogClick(dialogInterface, BUTTON_CANCEL);
                        break;
                }
            }
        };
        builder.setPositiveButton(android.R.string.ok, clickListener);
        builder.setNegativeButton(android.R.string.cancel, clickListener);
        return builder.show();
    }

    public AlertDialog createInputDialog(int title, final DialogInputCallBack inputCallBack){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final EditText inputEditor = new EditText(context);
        inputEditor.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setTitle(title);
        builder.setView(inputEditor);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (inputCallBack == null) return;
                inputCallBack.onDialogInput(dialogInterface, inputEditor.getText().toString());
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        return builder.show();
    }

//    public TimePickerDialog showTimePickerDialog(BaseActivity activity, final OnTimePickerCallBack callBack, int hourOfDay, int minute, boolean is24HourMode){
//        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
//                if (callBack == null) return;
//                callBack.onTimeSet(hourOfDay, minute, second);
//            }
//        }, hourOfDay, minute, is24HourMode);
//        timePickerDialog.show(activity.getFragmentManager(), "DatePickerDialog");
//        return timePickerDialog;
//    }

    public interface OnTimePickerCallBack{

        void onTimeSet(int hourOfDay, int minute, int second);
    }
}
