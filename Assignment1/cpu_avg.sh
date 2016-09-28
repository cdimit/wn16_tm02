#!/bin/bash

# commant to export cpu load every minute
cat /proc/loadavg | cut -f 1 -d " "
