package com.kakaw.peepshow.manager;

import com.kakaw.peepshow.dao.UserServiceClient;
import com.kakaw.peepshow.dao.dto.UserDTO;
import com.squareup.otto.Bus;

import javax.inject.Inject;

/**
 * Created by keetaekhong on 11/15/14.
 */
public class UserActivitySummaryManagerImpl extends BaseManager implements UserActivitySummaryManager {

    @Inject
    Bus bus;

    @Inject
    UserServiceClient mUserServiceClient;

    @Override
    public UserRetrieveCompleteEvent getUser() {
        UserDTO user = mUserServiceClient.getUser("1");
        return new UserRetrieveCompleteEvent(user);
    }

    public class UserRetrieveCompleteEvent {
        private UserDTO mUserDTO;
        public UserRetrieveCompleteEvent(UserDTO userDto) {
            this.mUserDTO = userDto;
        }
        public UserDTO getmUserDTO() {
            return mUserDTO;
        }
    }
}
