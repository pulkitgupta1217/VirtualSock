package pgupta.virtualsock.Facade;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import pgupta.virtualsock.Model.User;

/**
 * Created by pulki on 10/14/2017.
 */

public class Facade {

    private static User user;
    private static FirebaseUser fireUser;
    private static FirebaseAuth instance;


    public static void login(final String email, String password, final Callback<AuthTuple> callback) {
        String errorMessage = "";
        final AuthTuple tuple = new AuthTuple();
        if (email == null || email.isEmpty()) {
            errorMessage += "Enter email address or username";
        } else if (password == null || password.isEmpty()) {
            errorMessage += "Enter password!";
        } else if (password.length() < 6) {
            errorMessage += "Password too short, enter minimum 6 characters!";
        }
        tuple.setErrorMessage(errorMessage);
        if (!errorMessage.isEmpty()) {
            tuple.setSuccess(false);
        } else  {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        // there was an error
                        tuple.setErrorMessage(tuple.getErrorMessage() + "Failed to Login");
                        tuple.setSuccess(false);
                        callback.accept(tuple);
                    } else {
                        tuple.setSuccess(true);
                        fireUser = task.getResult().getUser();

                        FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                user = dataSnapshot.child("users").child(fireUser.getUid()).getValue(User.class);
                                callback.accept(tuple);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
            });
        }

    }

    public static void sendResetInstructions(String email, final Callback<String> callback) {
        instance.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String message;
                        if (task.isSuccessful()) {
                            message = "We have sent you instructions to reset your password!";
                        } else {
                            message = "Failed to send reset email!";
                        }
                        callback.accept(message);
                    }
                });
    }

    public static void signUp(final String email, String password, final Callback<AuthTuple> callback) {
        String errorMessage = "";
        final AuthTuple tuple = new AuthTuple();
        if (password == null || password.isEmpty()) {
            errorMessage = errorMessage + "Enter password!";
        } else if (password.length() < 6) {
            errorMessage = errorMessage + "Password too short, enter minimum 6 characters!";
        } else if (email == null || email.isEmpty()){
            errorMessage = errorMessage +"invalid Email";
        }
        tuple.setErrorMessage(errorMessage);

        if (!errorMessage.isEmpty()) {
            tuple.setSuccess(false);
        } else {
            //TODO: fix
            instance.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            String error = "";
                            //CODE ADDED BY PULKIT FOR SINGLETON

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                tuple.setSuccess(false);
                                tuple.setErrorMessage(tuple.getErrorMessage() + "Auth failed");
                                callback.accept(tuple);
                            } else {
                                fireUser = task.getResult().getUser();
                                FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        user = dataSnapshot.child("users").child(fireUser.getUid()).getValue(User.class);
                                        callback.accept(tuple);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }
                        }
                    });
        }
    }

    public static void setLight(String color, Callback<Boolean> callback) {
        //TODO get Stuff to determine status of light
    }

    public static void updateLights(Callback<List<String>[]> updateCallback) {
        //TODO
    }

    public static void loadProfile(Callback<Object[]> callback) {
        //todo: get info from server
    }

    public static void updateProfile(String name, String username, Callback<String> callback) {
        //TODO: update userInfo
    }
}
