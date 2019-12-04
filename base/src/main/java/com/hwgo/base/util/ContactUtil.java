package com.hwgo.base.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import com.hwgo.base.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * <br> ClassName:   ContactUtil
 * <br> Description: 手机联系人工具类
 * <br>
 */
public class ContactUtil {
    private static Map<String, String> mContacts;
    private static boolean isPrepared = false;

    /**
     * <br> Description: 获取手机联系人数据
     *
     * @param context 上下文
     * @return 返回联系人集合数据
     */
    public static synchronized Map<String, String> getContactsMap(final Context context) {
        if (mContacts == null || !isPrepared) {
            if (mContacts == null) {
                mContacts = new TreeMap<>();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mContacts.putAll(getContactsUtil(context));
                        isPrepared = true;
                    }
                }).start();
                return null;
            }
            if (!isPrepared) {
                return null;
            }
        }
        return mContacts;
    }

    /**
     * <br> Description: 获取联系人信息
     *
     * @return 联系人信息集合
     */
    public static List<Contacts> getContacts(Context context) {
        List<Contacts> list = new ArrayList<>();
        list = getPhoneContacts(context, list);
        return list;
    }

    private static synchronized Map<String, String> getContactsUtil(Context context) {
        Map<String, String> mContacts_temp = new TreeMap<>();
        try {
            String[] mContactsProjection = new String[]{
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                    // ContactsContract.CommonDataKinds.Phone.TYPE,
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.Contacts.DISPLAY_NAME,
                    /* ContactsContract.Contacts.PHOTO_ID */};
            ContentResolver resolver = context.getContentResolver();
            Cursor phoneCursor = resolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    mContactsProjection, null, null, null);
            while (phoneCursor != null && phoneCursor.moveToNext()) {
                Long CONTACT_ID = phoneCursor.getLong(0);
                String NUMBER = phoneCursor.getString(1);
                String DISPLAY_NAME = phoneCursor.getString(2);
                mContacts_temp.put(NUMBER == null ? "" : NUMBER, DISPLAY_NAME == null ? "" : DISPLAY_NAME);
            }
        } catch (Exception e) {
        }
        return mContacts_temp;
    }

    /**
     * <br> Description: 根据联系人ID查询联系人
     *
     * @param context 上下文
     * @param cursor  联系人cursor
     * @return 返回联系人Contact对象
     */
    public static Contact getContactPhone(Context context, Cursor cursor) {
        int phoneColumn = cursor
                .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
        int phoneNum = cursor.getInt(phoneColumn);
        if (phoneNum > 0) {
            // 获得联系人的ID号
            int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            String contactId = cursor.getString(idColumn);
            // 获得联系人电话的cursor
            Cursor phone = context.getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
                            + contactId, null, null);
            String personName = "";
            ArrayList<String> phoneList = new ArrayList<String>();
            if (phone != null && phone.moveToFirst()) {
                for (; !phone.isAfterLast(); phone.moveToNext()) {
                    int index_phone = phone
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    int index_name = phone
                            .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                    personName = phone.getString(index_name);
                    phoneList.add(phone.getString(index_phone));
                }
                if (!phone.isClosed()) {
                    phone.close();
                }
                Contact contact = new Contact();
                contact.setName(personName);
                contact.setPhoneNum(phoneList);
                return contact;
            }
        }
        return null;
    }

    /**
     * <br> Description: 选择一个号码
     *
     * @param activity        页面activity对象
     * @param context         上下文
     * @param phoneNum      要筛选的号码集合
     * @param changePhoneListener 成功选中号码的回调接口类
     */
    public static void changePhoneNum(Activity activity, Context context, final ArrayList<String> phoneNum, final ChangePhoneNumListener changePhoneListener) {
        if (activity.isFinishing()) {
            return;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.base_util_contact_layout, null);
        ((TextView) view.findViewById(R.id.label)).setText("请选择号码");
        List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();
        for (String str : phoneNum) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("item_text", str);
            listData.add(map);
        }
        SimpleAdapter mSAdapter = new SimpleAdapter(context, listData,
                R.layout.base_util_contact_layout_item, new String[]{
                "item_text"}, new int[]{
                R.id.itemText});
        final AlertDialog dialog = new AlertDialog.Builder(activity).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        dialog.setContentView(view);
        ListView listview = view.findViewById(R.id.custom_spinner_list);
        listview.setDivider(new ColorDrawable(view.getContext().getResources().getColor(R.color.base_util_contact_dividing_line_light)));
        listview.setDividerHeight(UiUtil.dp2px(0.5f));
        listview.setAdapter(mSAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (changePhoneListener != null) {
                    // 选中的号码
                    changePhoneListener.onChangePhone(phoneNum.get(i));
                }
                dialog.dismiss();
            }
        });
    }



    /**
     * <br> Description: 得到手机通讯录联系人信息
     *
     * @param list 通讯录联系人存储集合
     * @return 联系人信息集合
     */
    private static List<Contacts> getPhoneContacts(Context context, List<Contacts> list) {
        Cursor phoneCursor = null;
        Cursor phone = null;
        try {
            String[] PHONES_PROJECTION = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.CONTACT_ID};
            ContentResolver resolver = context.getContentResolver();
            // 获取手机联系人
            phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);

            if (phoneCursor != null) {
                while (phoneCursor.moveToNext()) {
                    //得到联系人名称
                    String contactName = phoneCursor.getString(0);
                    //得到联系人ID
                    Long contactId = phoneCursor.getLong(2);
                    phone = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                    Set setPhone = new HashSet();
                    while (phone.moveToNext()) {
                        String userNumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        setPhone.add(new String(userNumber));
                    }
                    String phoneNumber = "";
                    if (setPhone.size() > 0) {
                        phoneNumber = setPhone.toString();
                        phoneNumber = phoneNumber.substring(1, phoneNumber.length() - 1);
                    }
                    if (TextUtils.isEmpty(phoneNumber)) {
                        continue;
                    }
                    list.add(new Contacts(contactName, phoneNumber));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (phone != null) {
                phone.close();
            }
            if (phoneCursor != null) {
                phoneCursor.close();
            }
        }
        return list;
    }

    public interface ChangePhoneNumListener {
        void onChangePhone(String changePhone);
    }

    static class Contacts implements Serializable {
        /**
         * 姓名
         */
        private String Name;
        /**
         * 手机号码
         */
        private String TelNo;

        public Contacts() {
        }

        public Contacts(String name, String telNo) {
            Name = name;
            TelNo = telNo;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getTelNo() {
            return TelNo;
        }

        public void setTelNo(String telNo) {
            TelNo = telNo;
        }
    }

    static class Contact {
        private String name;
        private ArrayList<String> phoneNum;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ArrayList<String> getPhoneNum() {
            return phoneNum;
        }

        public void setPhoneNum(ArrayList<String> phoneNum) {
            this.phoneNum = phoneNum;
        }
    }

}
