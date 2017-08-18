
local table_res
table_res = redis.call("hmGet",KEYS[1],KEYS[2])
if table_res[1]==false then
    redis.call("hmSet",KEYS[1],KEYS[2],1)
    redis.call("expire",KEYS[1], ARGV[1])
else
    redis.call("hmSet",KEYS[1],KEYS[2],table_res[1]+1)
end;