package com.neterbox.qb;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBDialogType;
import com.quickblox.chat.utils.DialogUtils;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class QbDialogUtils {
    private static final String TAG = QbDialogUtils.class.getSimpleName();



    public static QBChatDialog createDialog(List<QBUser> users) {
        try
        {
            QBUser currentUser = ChatHelper.getCurrentUser();
            users.remove(currentUser);

            return DialogUtils.buildDialog(users.toArray(new QBUser[users.size()]));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }

    public static List<QBUser> getAddedUsers(QBChatDialog dialog, List<QBUser> currentUsers) {
        return getAddedUsers(getQbUsersFromQbDialog(dialog), currentUsers);
    }

    public static List<QBUser> getAddedUsers(List<QBUser> previousUsers, List<QBUser> currentUsers) {
        try
        {
            List<QBUser> addedUsers = new ArrayList<>();
            for (QBUser currentUser : currentUsers) {
                boolean wasInChatBefore = false;
                for (QBUser previousUser : previousUsers) {
                    if (currentUser.getId().equals(previousUser.getId())) {
                        wasInChatBefore = true;
                        break;
                    }
                }
                if (!wasInChatBefore) {
                    addedUsers.add(currentUser);
                }
            }

            QBUser currentUser = ChatHelper.getCurrentUser();
            addedUsers.remove(currentUser);

            return addedUsers;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }

    public static List<QBUser> getRemovedUsers(QBChatDialog dialog, List<QBUser> currentUsers) {
        return getRemovedUsers(getQbUsersFromQbDialog(dialog), currentUsers);
    }

    public static List<QBUser> getRemovedUsers(List<QBUser> previousUsers, List<QBUser> currentUsers) {
        try
        {
            List<QBUser> removedUsers = new ArrayList<>();
            for (QBUser previousUser : previousUsers) {
                boolean isUserStillPresented = false;
                for (QBUser currentUser : currentUsers) {
                    if (previousUser.getId().equals(currentUser.getId())) {
                        isUserStillPresented = true;
                        break;
                    }
                }
                if (!isUserStillPresented) {
                    removedUsers.add(previousUser);
                }
            }

            QBUser currentUser = ChatHelper.getCurrentUser();
            removedUsers.remove(currentUser);

            return removedUsers;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }

    public static void logDialogUsers(QBChatDialog qbDialog) {
        Log.v(TAG, "Dialog " + getDialogName(qbDialog));
        logUsersByIds(qbDialog.getOccupants());
    }

    public static void logUsers(List<QBUser> users) {
        try
        {
            for (QBUser user : users) {
                Log.i(TAG, user.getId() + " " + user.getFullName());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    private static void logUsersByIds(List<Integer> users) {
        try
        {
            for (Integer id : users) {
                QBUser user = QbUsersHolder.getInstance().getUserById(id);
                Log.i(TAG, user.getId() + " " + user.getFullName());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public static Integer[] getUserIds(List<QBUser> users) {
        ArrayList<Integer> ids = new ArrayList<>();
        for (QBUser user : users) {
            ids.add(user.getId());
        }
        return ids.toArray(new Integer[ids.size()]);
    }

    public static String getDialogName(QBChatDialog dialog) {
        try
        {
            if (dialog.getType().equals(QBDialogType.GROUP)) {
                return dialog.getName();
            } else {
                // It's a private dialog, let's use opponent's name as chat name
                Integer opponentId = dialog.getRecipientId();
                QBUser user = QbUsersHolder.getInstance().getUserById(opponentId);
                Log.e("qbdialog uytilsuser",":"+new Gson().toJson(user));
                if (user != null) {
                    if(user.getLogin()==null)
                    {
                        return  user.getEmail();
                    }
                    else
                    {
                        return user.getLogin();
                    }
//                    String name=TextUtils.isEmpty(user.getEmail()) ? user.getEmail() : user.getLogin();
//                    Log.e("login",name);
//                    return TextUtils.isEmpty(user.getEmail()) ? user.getEmail() : user.getLogin();
                } else {
                    return dialog.getName();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }

    private static List<QBUser> getQbUsersFromQbDialog(QBChatDialog dialog) {
        try
        {
            List<QBUser> previousDialogUsers = new ArrayList<>();
            for (Integer id : dialog.getOccupants()) {
                QBUser user = QbUsersHolder.getInstance().getUserById(id);
                if (user == null) {
                    throw new RuntimeException("User from dialog is not in memory. This should never happen, or we are screwed");
                }
                previousDialogUsers.add(user);
            }
            return previousDialogUsers;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }

    public static List<Integer> getOccupantsIdsListFromString(String occupantIds) {
        try
        {
            List<Integer> occupantIdsList = new ArrayList<>();
            String[] occupantIdsArray = occupantIds.split(",");
            for (String occupantId : occupantIdsArray) {
                occupantIdsList.add(Integer.valueOf(occupantId));
            }
            return occupantIdsList;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }

    public static String getOccupantsIdsStringFromList(Collection<Integer> occupantIdsList) {
        return TextUtils.join(",", occupantIdsList);
    }

    public static QBChatDialog buildPrivateChatDialog(String dialogId, Integer recipientId){
        QBChatDialog chatDialog = DialogUtils.buildPrivateDialog(recipientId);
        chatDialog.setDialogId(dialogId);

        return chatDialog;
    }
}
