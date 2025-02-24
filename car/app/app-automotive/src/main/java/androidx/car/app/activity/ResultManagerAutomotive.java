/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.car.app.activity;

import android.content.ComponentName;
import android.content.Intent;

import androidx.annotation.RestrictTo;
import androidx.car.app.annotations.CarProtocol;
import androidx.car.app.managers.ResultManager;

import org.jspecify.annotations.Nullable;

/**
 * Automotive implementation of {@link ResultManager}
 *
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
@CarProtocol
public class ResultManagerAutomotive implements ResultManager {
    @Override
    public void setCarAppResult(int resultCode, @Nullable Intent data) {
        CarAppViewModel.setActivityResult(resultCode, data);
    }

    @Override
    public @Nullable ComponentName getCallingComponent() {
        return CarAppViewModel.getCallingActivity();
    }
}
