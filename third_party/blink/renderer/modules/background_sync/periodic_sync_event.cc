// Copyright 2019 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

#include "third_party/blink/renderer/modules/background_sync/periodic_sync_event.h"

namespace blink {

PeriodicSyncEvent::PeriodicSyncEvent(const AtomicString& type,
                                     const String& tag,
                                     WaitUntilObserver* observer)
    : ExtendableEvent(type, ExtendableEventInit::Create(), observer),
      tag_(tag) {}

PeriodicSyncEvent::PeriodicSyncEvent(const AtomicString& type,
                                     const PeriodicSyncEventInit* init)
    : ExtendableEvent(type, init), tag_(init->tag()) {}

PeriodicSyncEvent::~PeriodicSyncEvent() = default;

const AtomicString& PeriodicSyncEvent::InterfaceName() const {
  return event_interface_names::kPeriodicSyncEvent;
}

const String& PeriodicSyncEvent::tag() const {
  return tag_;
}

}  // namespace blink
