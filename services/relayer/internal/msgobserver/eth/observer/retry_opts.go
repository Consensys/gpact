package observer

import "time"

type FailureRetryOpts struct {
	RetryAttempts uint
	RetryDelay    time.Duration
}

var DefaultRetryOptions = FailureRetryOpts{
	RetryAttempts: 5,
	RetryDelay:    300 * time.Millisecond,
}
